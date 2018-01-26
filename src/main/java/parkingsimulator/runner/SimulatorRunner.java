package parkingsimulator.runner;

import parkingsimulator.controllers.SimulatorController;

public class SimulatorRunner {
    public static void main(String[] args) {
        SimulatorController simulator = new SimulatorController();

        simulator.run(100);
    }
}
