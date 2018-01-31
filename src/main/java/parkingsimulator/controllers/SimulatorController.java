package parkingsimulator.controllers;

import parkingsimulator.models.*;
import parkingsimulator.views.CarParkView;
import parkingsimulator.views.StatsView;
import parkingsimulator.views.SimulatorView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SimulatorController extends AbstractController<CarParkView, SimulatorViewModel, StatsView> implements ActionListener
{
    private enum CarType
    {
        AD_HOC, PASS, RESERVATION
    }

    private Calendar dateTime;

    private int tickPause = 1;

    private int weekDayArrivals = 200; // average number of arriving cars per hour
    private int weekendArrivals = 400; // average number of arriving cars per hour
    private int weekDayPassArrivals = 100; // average number of arriving cars per hour
    private int weekendPassArrivals = 200; // average number of arriving cars per hour

    private int enterSpeed = 3; // number of cars that can enter per minute
    private int paymentSpeed = 7; // number of cars that can pay per minute
    private int exitSpeed = 5; // number of cars that can leave per minute

    private boolean isRunning = true;

    private JButton oneMinute;
    private JButton hundredMinutes;
    private JButton stopSimulating;

    public SimulatorController() {
        SimulatorViewModel model = new SimulatorViewModel(3, 6, 28);
        CarParkView carParkView = new CarParkView(model);
        StatsView statsView = new StatsView(model);
        SimulatorView view = new SimulatorView(carParkView, this, statsView);

        dateTime = Calendar.getInstance();
        dateTime.set(2018,Calendar.JANUARY,1,0,0);

        this.setModel(model);
        this.setCarParkView(carParkView);
        this.setStatsView(statsView);

        oneMinute = new JButton("1 minute");
        oneMinute.addActionListener(this);
        hundredMinutes = new JButton("100 minutes");
        hundredMinutes.addActionListener(this);
        stopSimulating = new JButton("Start/ stop");
        stopSimulating.addActionListener(this);

        this.setLayout(new FlowLayout());
        add(oneMinute);
        add(hundredMinutes);
        add(stopSimulating);
    }

    public void actionPerformed(ActionEvent e) {

        Thread thread = new Thread(() -> {
            if (e.getSource() == oneMinute) run(1);
            if (e.getSource() == hundredMinutes) run(100);
            if (e.getSource() == stopSimulating) toggle();
        });
        thread.start();
    }

    public void run(int steps) {
        //createRandomReservations(1);
        if (!isRunning) isRunning = true;

        for (int i = 0; i < steps && isRunning; i++)
            tick();

    }

    public void toggle() {
        isRunning = isRunning ? false : true;
        if (isRunning) {
            run(10000);
        }
    }

    private void tick() {
        advanceTime();
        updateViews();
        // Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        createRandomReservations((new Random()).nextInt(5));

        handleEntrance();
        handleExit();
    }

    public void tickCars() {
        for (int floor = 0; floor < this.getModel().getNumberOfFloors(); floor++) {
            for (int row = 0; row < this.getModel().getNumberOfRows(); row++) {
                for (int place = 0; place < this.getModel().getNumberOfPlaces(); place++) {
                    Location location = this.getModel().getLocations()[floor][row][place];

                    if (location.getCar() != null)
                        location.getCar().tick();
                }
            }
        }
    }

    private void advanceTime() {
        // Advance the time by one minute.
        dateTime.add(Calendar.MINUTE, 1);

        // Reset revenues at the end of a week
        if(dateTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && dateTime.get(Calendar.HOUR_OF_DAY) == 23 && dateTime.get(Calendar.MINUTE) == 59) {
            this.getModel().resetRevenues();
        }
    }

    private void handleEntrance() {
        carsArriving();
        carsEntering(this.getModel().getEntrancePassQueue());
        carsEntering(this.getModel().getEntranceCarQueue());
        carsEntering(this.getModel().getEntranceReservationQueue());
    }

    private void handleExit() {
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    private void updateViews() {
        tickCars();
        // Update the car park view.
        this.getCarParkView().updateView();
        this.getStatsView().updateView();
    }

    private void carsArriving() {
        int numberOfCars = getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, CarType.AD_HOC);

        numberOfCars = getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, CarType.PASS);

        numberOfCars = getNumberOfReservationCarsArriving();
        addArrivingCars(numberOfCars, CarType.RESERVATION);
    }

    private void carsEntering(CarQueue queue) {
        int i = 0;
        // Remove car from the front of the queue and assign to a parking space.
        while (queue.carsInQueue() > 0 &&
                this.getModel().getNumberOfOpenSpots() > 0 &&
                i < enterSpeed) {
            Car car = queue.removeCar();
            Location freeLocation = null;

            if (car instanceof ParkingPassCar) {
                freeLocation = getFirstFreePassLocation();
                if (freeLocation == null) {
                    freeLocation = getFirstFreeLocation();
                }
            }
            else if(car instanceof ReservationCar){
                freeLocation = car.getLocation();

                if (freeLocation == null){
                    freeLocation = getFirstFreeLocation();
                }
            }
            else {
                freeLocation = getFirstFreeLocation();
            }

            setCarAt(freeLocation, car);
            i++;
        }
    }

    private void carsReadyToLeave() {
        // Add leaving cars to the payment queue.
        Car car = getFirstLeavingCar();

        while (car != null) {
            if (car.getHasToPay()) {
                car.setIsPaying(true);
                this.getModel().getPaymentCarQueue().addCar(car);
            }
            else {
                carLeavesSpot(car);
            }
            car = getFirstLeavingCar();
        }
    }

    private void carsPaying() {
        // Let cars pay.
        int i = 0;
        while (this.getModel().getPaymentCarQueue().carsInQueue() > 0 && i < paymentSpeed) {
            Car car = this.getModel().getPaymentCarQueue().removeCar();
            // TODO Handle payment.
            BigDecimal price = car.getPrice();

            int dayOfWeek = dateTime.get(Calendar.DAY_OF_WEEK);

            if(dayOfWeek==2) {
                this.getModel().addToRevenueMonday(price);
            }
            if(dayOfWeek==3) {
                this.getModel().addToRevenueTuesday(price);
            }
            if(dayOfWeek==4) {
                this.getModel().addToRevenueWednesday(price);
            }
            if(dayOfWeek==5) {
                this.getModel().addToRevenueThursday(price);
            }
            if(dayOfWeek==6) {
                this.getModel().addToRevenueFriday(price);
            }
            if(dayOfWeek==7) {
                this.getModel().addToRevenueSaturday(price);
            }
            if(dayOfWeek==1) {
                this.getModel().addToRevenueSunday(price);
            }

            this.getModel().addToRevenue(price);

            carLeavesSpot(car);
            i++;
        }
    }

    private void carsLeaving() {
        // Let cars leave.
        int i = 0;
        while (this.getModel().getExitCarQueue().carsInQueue() > 0 && i < exitSpeed) {
            this.getModel().getExitCarQueue().removeCar();
            i++;
        }
    }

    /**
     * @param weekDay
     * @param weekend
     * @return the number of cars that arrive per minute
     */
    private int getNumberOfCars(int weekDay, int weekend) {
        Random random = new Random();

        boolean isWeekend = dateTime.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || dateTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = isWeekend
                ? weekend
                : weekDay;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int) Math.round(numberOfCarsPerHour / 60);
    }

    /**
     * @param numberOfCars
     * @param type
     */
    private void addArrivingCars(int numberOfCars, CarType type) {
        // Add the cars to the back of the queue.
        switch (type) {
            case AD_HOC:
                for (int i = 0; i < numberOfCars; i++) {
                    this.getModel().getEntranceCarQueue().addCar(new AdHocCar());
                }
                break;
            case PASS:
                for (int i = 0; i < numberOfCars; i++) {
                    this.getModel().getEntrancePassQueue().addCar(new ParkingPassCar());
                }
                break;
            case RESERVATION:
                for (int i = 0; i < numberOfCars; i++) {
                    ReservationCar reservationCar = new ReservationCar();

                    Reservation reservation = getFirstAvailableReservation();

                    reservationCar.setLocation(reservation.getLocation());

                    long timeDifferenceInMillis = (reservation.getEnd().getTimeInMillis() - reservation.getBegin().getTimeInMillis());
                    int stayMinutes = (int)TimeUnit.MILLISECONDS.toMinutes(timeDifferenceInMillis);

                    reservationCar.setStayMinutes(stayMinutes);

                    this.getModel().getEntranceCarQueue().addCar(reservationCar);

                    removeReservation(reservation);
                }
                break;
        }
    }

    /**
     * @param car
     */
    private void carLeavesSpot(Car car) {
        removeCarAt(car.getLocation());
        this.getModel().getExitCarQueue().addCar(car);
    }

    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < this.getModel().getNumberOfFloors(); floor++) {
            for (int row = 0; row < this.getModel().getNumberOfRows(); row++) {
                for (int place = 0; place < this.getModel().getNumberOfPlaces(); place++) {
                    Location location = this.getModel().getLocations()[floor][row][place];

                    if (location.getCar() == null && !location.isForSubscriber()) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    public Location getFirstFreePassLocation() {
        for (int floor = 0; floor < this.getModel().getNumberOfFloors(); floor++) {
            for (int row = 0; row < this.getModel().getNumberOfRows(); row++) {
                for (int place = 0; place < this.getModel().getNumberOfPlaces(); place++) {
                    Location location = this.getModel().getLocations()[floor][row][place];
                    if (location.getCar() == null && location.isForSubscriber()) return location;
                }
            }
        }
        return null;
    }

    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < this.getModel().getNumberOfFloors(); floor++) {
            for (int row = 0; row < this.getModel().getNumberOfRows(); row++) {
                for (int place = 0; place < this.getModel().getNumberOfPlaces(); place++) {
                    Location location = this.getModel().getLocations()[floor][row][place];

                    if (location.getCar() != null && location.getCar().getMinutesLeft() <= 0 && !location.getCar().getIsPaying())
                        return location.getCar();
                }
            }
        }
        return null;
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();

        if (floor < 0 || floor >= this.getModel().getNumberOfFloors() || row < 0 || row > this.getModel().getNumberOfRows() || place < 0 || place > this.getModel().getNumberOfPlaces()) {
            return false;
        }

        return true;
    }

    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }

        Car oldCar = location.getCar();

        if (oldCar == null) {
            location.setCar(car);
            car.setLocation(location);

            this.getModel().decrementNumberOfOpenSpots();

            return true;
        }
        return false;
    }

    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }

        Car car = location.getCar();

        if (car == null) {
            return null;
        }

        location.setCar(null);
        car.setLocation(null);

        this.getModel().incrementNumberOfOpenSpots();

        return car;
    }

    public void createReservation(Location location, Calendar begin, Calendar end) {
        getModel().getReservations().add(new Reservation(location, begin, end));
    }

    public void removeReservation(Reservation reservation) {
        getModel().getReservations().remove(reservation);
    }

    public boolean canReserveLocation(Location location, Calendar begin, Calendar end) {
        Reservation r = new Reservation(location, begin, end);

        for (Reservation reservation : getModel().getReservations())
            if (reservation.overlapsWith(r))
                return false;

        return true;
    }

    public Location findFirstAvailableLocationForReservation(Calendar begin, Calendar end) {
        for (int floor = 0; floor < getModel().getNumberOfFloors(); floor++) {
            for (int row = 0; row < getModel().getNumberOfRows(); row++) {
                for (int place = 0; place < getModel().getNumberOfPlaces(); place++) {
                    Location location = getModel().getLocations()[floor][row][place];

                    if (canReserveLocation(location, begin, end))
                        return location;
                }

            }
        }

        return null;
    }

    public void createRandomReservations(int count) {
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            Calendar begin = dateTime;
            begin.add(Calendar.HOUR_OF_DAY, random.nextInt(25));

            Calendar end = (Calendar)begin.clone();
            end.add(Calendar.MINUTE, random.nextInt(300));

            Location location = findFirstAvailableLocationForReservation(begin, end);

            if (location != null) {
                createReservation(location, begin, end);
            }
        }
    }

    public int getNumberOfReservationCarsArriving() {
        int count = 0;

        for (Reservation reservation : getModel().getReservations()) {
            if (!dateTime.before(reservation.getBegin())
                    && !dateTime.after(reservation.getEnd())
                    && reservation.getLocation() != null) {
                if (reservation.getLocation().getCar() == null){
                    count++;
                }
            }
        }

        return count;
    }

    public Reservation getFirstAvailableReservation(){
        for (Reservation reservation : getModel().getReservations()) {
            if (!dateTime.before(reservation.getBegin())
                    && !dateTime.after(reservation.getEnd())
                    && reservation.getLocation() != null) {
                if (reservation.getLocation().getCar() == null){
                    return reservation;
                }
            }
        }

        return null;
    }
}