package parkingsimulator.models;

public class SimulatorViewModel extends Model
{
    private Car[][][] cars;

    public SimulatorViewModel(int numberOfFloors, int numberOfRows, int numberOfPlaces){
        this.cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
    }

    public Car[][][] getCars() {
        return this.cars;
    }
}
