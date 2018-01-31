package parkingsimulator.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SimulatorViewModel extends AbstractModel {
    private Location[][][] locations;
    private List<Reservation> reservations;
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

    private int parkingPassLocations = 100; // number of locations reserved for parking pass holders
    private int parkingPassHolders = 200; // number of parking pass holders

    public SimulatorViewModel(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots = numberOfFloors * numberOfRows * numberOfPlaces;

        revenue = new BigDecimal(0);

        locations = new Location[numberOfFloors][numberOfRows][numberOfPlaces];
        reservations = new ArrayList<>();

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

    public void addToRevenue(BigDecimal price) {
        revenue = revenue.add(price);
    }

    public void subtractFromRevenue(BigDecimal price){
        revenue = revenue.subtract(price);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}