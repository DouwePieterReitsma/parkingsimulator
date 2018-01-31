package parkingsimulator.models;

import java.math.BigDecimal;

public class SimulatorViewModel extends AbstractModel {
    private Location[][][] locations;
    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue entranceReservationQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private BigDecimal revenue;
    private BigDecimal revenueMonday;
    private BigDecimal revenueTuesday;
    private BigDecimal revenueWednesday;
    private BigDecimal revenueThursday;
    private BigDecimal revenueFriday;
    private BigDecimal revenueSaturday;
    private BigDecimal revenueSunday;

    private int parkingPassLocations = 100; // number of locations reserved for parking pass holders
    private int parkingPassHolders = 200; // number of parking pass holders

    public SimulatorViewModel(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots = numberOfFloors * numberOfRows * numberOfPlaces;

        revenue = new BigDecimal(0);
        revenueMonday = new BigDecimal(0);
        revenueTuesday = new BigDecimal(0);
        revenueWednesday = new BigDecimal(0);
        revenueThursday = new BigDecimal(0);
        revenueFriday = new BigDecimal(0);
        revenueSaturday = new BigDecimal(0);
        revenueSunday = new BigDecimal(0);

        locations = new Location[numberOfFloors][numberOfRows][numberOfPlaces];

        int reservePlaces = parkingPassLocations;

        // do not reserve more locations than pass holders
        if(parkingPassLocations > parkingPassHolders) {
            reservePlaces = parkingPassHolders;
        }

        // reserve the locations for pass holders
        for(int floor = 0; floor < numberOfFloors; floor++)
        {
            for(int row = 0; row < numberOfRows; row++)
            {
                for (int place = 0; place < numberOfPlaces; place++)
                {
                    locations[floor][row][place] = new Location(floor, row, place);

                    if(reservePlaces > 0) {
                        locations[floor][row][place].setForSubscriber(true);
                        reservePlaces--;
                    }

                }
            }
        }

        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        entranceReservationQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
    }

    public CarQueue getEntranceCarQueue() {
        return entranceCarQueue;
    }

    public int getEntranceCarQueueSize() {
        return entranceCarQueue.carsInQueue();
    }

    public CarQueue getEntrancePassQueue() {
        return entrancePassQueue;
    }

    public CarQueue getPaymentCarQueue() {
        return paymentCarQueue;
    }

    public CarQueue getExitCarQueue() {
        return exitCarQueue;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public int getNumberOfOpenSpots() {
        return numberOfOpenSpots;
    }

    public void incrementNumberOfOpenSpots() {
        numberOfOpenSpots++;
    }

    public void decrementNumberOfOpenSpots() {
        numberOfOpenSpots--;
    }

    public Location[][][] getLocations() {
        return locations;
    }

    public CarQueue getEntranceReservationQueue() {
        return entranceReservationQueue;
    }


    public BigDecimal getRevenue() {
        return revenue;
    }

    public BigDecimal getRevenueMonday() {
        return revenueMonday;
    }

    public BigDecimal getRevenueTuesday() {
        return revenueTuesday;
    }

    public BigDecimal getRevenueWednesday() {
        return revenueWednesday;
    }

    public BigDecimal getRevenueThursday() {
        return revenueThursday;
    }

    public BigDecimal getRevenueFriday() {
        return revenueFriday;
    }

    public BigDecimal getRevenueSaturday() {
        return revenueSaturday;
    }

    public BigDecimal getRevenueSunday() {
        return revenueSunday;
    }


    public void addToRevenue(BigDecimal price) {
        revenue = revenue.add(price);
    }

    public void addToRevenueMonday(BigDecimal price) {
        revenueMonday = revenueMonday.add(price);
    }

    public void addToRevenueTuesday(BigDecimal price) {
        revenueTuesday = revenueTuesday.add(price);
    }

    public void addToRevenueWednesday(BigDecimal price) {
        revenueWednesday = revenueWednesday.add(price);
    }

    public void addToRevenueThursday(BigDecimal price) {
        revenueThursday = revenueThursday.add(price);
    }

    public void addToRevenueFriday(BigDecimal price) {
        revenueFriday = revenueFriday.add(price);
    }

    public void addToRevenueSaturday(BigDecimal price) {
        revenueSaturday = revenueSaturday.add(price);
    }

    public void addToRevenueSunday(BigDecimal price) {
        revenueSunday = revenueSunday.add(price);
    }
    public void resetRevenues(){
        revenue = BigDecimal.ZERO;
        revenueMonday = BigDecimal.ZERO;
        revenueTuesday = BigDecimal.ZERO;
        revenueWednesday = BigDecimal.ZERO;
        revenueThursday = BigDecimal.ZERO;
        revenueFriday = BigDecimal.ZERO;
        revenueSaturday = BigDecimal.ZERO;
        revenueSunday = BigDecimal.ZERO;
    }
}