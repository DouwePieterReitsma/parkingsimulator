package parkingsimulator.controllers;

import parkingsimulator.models.*;
import parkingsimulator.views.SimulatorView;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class SimulatorController extends AbstractController<SimulatorView, SimulatorViewModel> {
    private enum CarType {
        AD_HOC, PASS, RESERVATION
    }

    private Calendar dateTime;

    private int tickPause = 100;

    private int weekDayArrivals = 100; // average number of arriving cars per hour
    private int weekendArrivals = 200; // average number of arriving cars per hour
    private int weekDayPassArrivals = 50; // average number of arriving cars per hour
    private int weekendPassArrivals = 5; // average number of arriving cars per hour

    private int enterSpeed = 3; // number of cars that can enter per minute
    private int paymentSpeed = 7; // number of cars that can pay per minute
    private int exitSpeed = 5; // number of cars that can leave per minute

    private boolean isRunning = true;

    public SimulatorController() {
        SimulatorViewModel model = new SimulatorViewModel(3, 3, 30);
        SimulatorView view = new SimulatorView(this, model);

        dateTime = Calendar.getInstance();
        dateTime.set(Calendar.YEAR, 1);
        dateTime.set(Calendar.MONTH, Calendar.JANUARY);
        dateTime.set(Calendar.DAY_OF_MONTH, 0);
        dateTime.set(Calendar.HOUR_OF_DAY, 0);
        dateTime.set(Calendar.MINUTE, 0);
        dateTime.set(Calendar.SECOND, 0);

        this.setModel(model);
        this.setView(view);
    }

    public void run(int steps) {
        //createRandomReservations(1);

        for (int i = 0; i < steps && isRunning; i++)
            tick();
    }

    public void toggle() {
        isRunning = isRunning ? false : true;
        if(isRunning) {
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

        handleEntrance();
        handleExit();
    }

    private void advanceTime() {
        // Advance the time by one minute.
        dateTime.add(Calendar.MINUTE, 1);
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
        this.getView().tick();
        // Update the car park view.
        this.getView().updateView();
    }

    private void carsArriving() {
        int numberOfCars = getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, CarType.AD_HOC);

        numberOfCars = getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, CarType.PASS);
    }

    private void carsEntering(CarQueue queue) {
        int i = 0;
        // Remove car from the front of the queue and assign to a parking space.
        while (queue.carsInQueue() > 0 &&
                this.getModel().getNumberOfOpenSpots() > 0 &&
                i < enterSpeed) {
            Car car = queue.removeCar();

            Location freeLocation = getFirstFreeLocation();

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
            } else {
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
                    this.getModel().getEntranceCarQueue().addCar(new ReservationCar());
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

                    if (location.getCar() == null)
                        return location;
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
}