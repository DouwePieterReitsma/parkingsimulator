package parkingsimulator;

import parkingsimulator.controllers.SimulatorController;

public class ParkingSimulator
{
    public static void main(String[] args) {
        SimulatorController simulator = new SimulatorController();

        simulator.run(100);
    }
}