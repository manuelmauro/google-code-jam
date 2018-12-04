package com.google.hashcode.aq;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public abstract class AbstractSimulation {
    public ProblemInstance pi;
    public Drone[] drones;
    public Warehouse[] warehouses;
    public Order[] orders;
    public int[] availabilities;
    public int t;

    public AbstractSimulation(ProblemInstance pi) {
        this.pi = pi;
        drones = pi.drones;
        availabilities = new int[drones.length];
        warehouses = pi.warehouses;
        orders = pi.orders;
        t = 0;
    }

    public List<Command> run() {
        List<Command> commands = new LinkedList<>();

        for (t = 0; t < 550; t++) {
            for (int d = 0; d < drones.length; d++) {
                if (isAvailable(d, t)) {
                    commands.add(nextCommand(d));
                }
            }
        }
        return commands;
    }

    private boolean isAvailable(int d, int t) {
        return t >= availabilities[d];
    }

    abstract public Command nextCommand(int d);
}
