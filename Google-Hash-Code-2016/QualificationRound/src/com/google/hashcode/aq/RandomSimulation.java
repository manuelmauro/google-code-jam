package com.google.hashcode.aq;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by manuel on 15/02/16.
 */
public class RandomSimulation extends AbstractSimulation {

    public RandomSimulation(ProblemInstance pi) {
        super(pi);
    }

    @Override
    public Command nextCommand(int d) {
        if (drones[d].isEmpty()) {
            return load(d);
        } else {
            return deliver(d);
        }
    }

    private Command load(int d) {
        int w = nearestWarehouse(drones[d]);
        if (w == -1){
            int wait = 100;
            availabilities[d] += wait;

            if (availabilities[d] >= pi.T) {
                availabilities[d] = pi.T;
                return Command.Wait(d, pi.T - t);
            } else {
                return Command.Wait(d, wait);
            }
        }
        int type = warehouses[w].maxProduct();
        int amount = 0;
        if (drones[d].canLoad(type, warehouses[w].products(type))) {
            amount = warehouses[w].products(type);
        } else {
            amount = drones[d].remainingCapacity() / pi.weights[type];
        }
        // Update
        int time = drones[d].load(warehouses[w].r(), warehouses[w].c(), type, amount);
        availabilities[d] += time;

        if (availabilities[d] >= pi.T) {
            availabilities[d] = pi.T;
            return Command.Wait(d, pi.T - t);
        } else {
            warehouses[w].load(type, amount);
            return Command.Load(d, w, type, amount);
        }
    }

    private Command deliver(int d) {
        int type = 0;
        while (drones[d].products(type) == 0) {
            type++;
        }
        int o = nearestOrder(drones[d], type);
        if (o == -1) {
            int w = (int) (Math.random()*warehouses.length);
            Command c = Command.Unload(d, w, type, drones[d].products(type));
            int amount = drones[d].products(type);
            int time = drones[d].unload(warehouses[w].r(), warehouses[w].c(), type, amount);
            availabilities[d] += time;
            if (availabilities[d] >= pi.T) {
                availabilities[d] = pi.T;
                return Command.Wait(d, pi.T - t);
            } else {
                warehouses[w].unload(type, amount);
                return c;
            }
        }
        int amount = 0;
        if (orders[o].products(type) <= drones[d].products(type)) {
            amount = orders[o].products(type);
        } else {
            amount = drones[d].products(type);
        }
        // Update
        int time = drones[d].deliver(orders[o].r(), orders[o].c(), type, amount);
        orders[o].deliver(type, amount);
        availabilities[d] += time;

        if (availabilities[d] >= pi.T) {
            availabilities[d] = pi.T;
            return Command.Wait(d, pi.T - t);
        } else {
            return Command.Deliver(d, o, type, amount);
        }
    }

    private int randomWarehouse(Drone drone) {
        int w = (int) (Math.random()*warehouses.length);
        while (w < warehouses.length && warehouses[w].isEmpty()) {
            w++;
        }
        if (w == warehouses.length) {
            return -1;
        } else {
            return w;
        }
    }

    private int randomOrder(Drone drone, int type) {
        int o = (int) (Math.random()*orders.length);
        while (o < orders.length && orders[o].products(type) == 0) {
           o++;
        }
        if (o == orders.length) {
            return -1;
        } else {
            return o;
        }
    }

    private int nearestWarehouse(Drone d) {
        int min = Integer.MAX_VALUE;
        int index = -1;

        for (int w = 0; w < warehouses.length; w++) {
            int dist = HashCodeUtils.distanceSquared(d.r(), d.c(), warehouses[w].r(), warehouses[w].c());
            if (dist < min) {
                min = dist;
                index = w;
            }
        }

        return index;
    }

    private int nearestOrder(Drone d, int type) {
        int min = Integer.MAX_VALUE;
        int index = -1;

        for (int o = 0; o < orders.length; o++) {
            if (orders[o].products(type) == 0)
                continue;;
            int dist = HashCodeUtils.distanceSquared(d.r(), d.c(), orders[o].r(), orders[o].c());
            if (dist < min) {
                min = dist;
                index = o;
            }
        }

        return index;
    }
}
