package parkingsimulator.models;

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

    public SimulatorViewModel(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots = numberOfFloors * numberOfRows * numberOfPlaces;

        locations = new Location[numberOfFloors][numberOfRows][numberOfPlaces];

        for(int floor = 0; floor < numberOfFloors; floor++)
        {
            for(int row = 0; row < numberOfRows; row++)
            {
                for (int place = 0; place < numberOfPlaces; place++)
                {
                    locations[floor][row][place] = new Location(floor, row, place);
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
}
