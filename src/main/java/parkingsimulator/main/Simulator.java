package parkingsimulator.main;

import parkingsimulator.controllers.*;

/**
 * Main class to setup the simulator itself.
 * @author Douwe Pieter Reitsma, Maiwand Rasuli, Matthijs Volker en Gertjan Haan.
 * @version 1.0.
 */

public class Simulator {
    private AbstractController simulatorController;

    /**
     * Constructor for the Simulator.
     */
    public Simulator() {
        simulatorController = new SimulatorController();
        simulatorController.run(1);
    }
}
