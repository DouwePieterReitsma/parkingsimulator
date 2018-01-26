package parkingsimulator.main;

import parkingsimulator.controllers.SimulatorController;

public class Simulator {
    private SimulatorController simulatorController;

    public Simulator() {
        simulatorController=new SimulatorController();

        simulatorController.run(100);
    }
}
