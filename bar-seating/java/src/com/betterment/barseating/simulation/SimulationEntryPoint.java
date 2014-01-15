package com.betterment.barseating.simulation;

import com.betterment.barseating.input.SimulationInput;

/**
 * @author Mike Matsui
 * @version $Id$
 * @since 1/15/14
 */
public interface SimulationEntryPoint {
    int runSimulation(SimulationInput input);

}
