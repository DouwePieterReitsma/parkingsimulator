package parkingsimulator.controllers;

import parkingsimulator.models.*;
import parkingsimulator.views.SimulatorView;

import java.util.Random;

public class SimulatorController extends Controller<SimulatorView, SimulatorViewModel>
{

    private static final String AD_HOC = "1";
    private static final String PASS = "2";

    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    private int tickPause = 100;

    private int weekDayArrivals = 100; // average number of arriving cars per hour
    private int weekendArrivals = 200; // average number of arriving cars per hour
    private int weekDayPassArrivals = 50; // average number of arriving cars per hour
    private int weekendPassArrivals = 5; // average number of arriving cars per hour

    private int enterSpeed = 3; // number of cars that can enter per minute
    private int paymentSpeed = 7; // number of cars that can pay per minute
    private int exitSpeed = 5; // number of cars that can leave per minute

    public SimulatorController() {
        SimulatorViewModel model = new SimulatorViewModel(3, 6, 30);
        SimulatorView view = new SimulatorView(model);

        this.setModel(model);
        this.setView(view);
    }

    public void run() {
        int steps = 10000;

        for (int i = 0; i < steps; i++) {
            tick();
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
    }

    private void advanceTime() {
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }

    }

    private void handleEntrance() {
        carsArriving();
        carsEntering(this.getModel().getEntrancePassQueue());
        carsEntering(this.getModel().getEntranceCarQueue());
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
        addArrivingCars(numberOfCars, AD_HOC);
        numberOfCars = getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);
    }

    private void carsEntering(CarQueue queue) {
        int i = 0;
        // Remove car from the front of the queue and assign to a parking space.
        while (queue.carsInQueue() > 0 &&
                this.getModel().getNumberOfOpenSpots() > 0 &&
                i < enterSpeed) {
            Car car = queue.removeCar();
            Location freeLocation = this.getView().getFirstFreeLocation();
            this.getView().setCarAt(freeLocation, car);
            i++;
        }
    }

    private void carsReadyToLeave() {
        // Add leaving cars to the payment queue.
        Car car = this.getView().getFirstLeavingCar();
        while (car != null) {
            if (car.getHasToPay()) {
                car.setIsPaying(true);
                this.getModel().getPaymentCarQueue().addCar(car);
            }
            else {
                carLeavesSpot(car);
            }
            car = this.getView().getFirstLeavingCar();
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

    private int getNumberOfCars(int weekDay, int weekend) {
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int) Math.round(numberOfCarsPerHour / 60);
    }

    private void addArrivingCars(int numberOfCars, String type) {
        // Add the cars to the back of the queue.
        switch (type) {
            case AD_HOC:
                for (int i = 0; i < numberOfCars; i++) {
                    this.getModel().getEntranceCarQueue().addCar(new AdHocCar());
                }
                break;
            case PASS:
                for (int i = 0; i < numberOfCars; i++) {
                    this.getModel().getEntrancePassQueue().addCar(new ParkingPassCar() {
                        @Override
                        public double getPrice() {
                            return 0;
                        }
                    })
                }
                break;
        }
    }

    private void carLeavesSpot(Car car) {
        this.getView().removeCarAt(car.getLocation());
        this.getModel().getExitCarQueue().addCar(car);
    }

}
