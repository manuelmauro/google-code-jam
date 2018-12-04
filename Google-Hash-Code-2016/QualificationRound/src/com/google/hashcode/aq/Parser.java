package com.google.hashcode.aq;

import java.util.List;

/**
 * Created by manuel on 13/02/16.
 */
public class Parser {
    public static ProblemInstance readInstance(List<String> lines) {
        ProblemInstance instance = new ProblemInstance();
        int offset = 0;

        // First line
        String[] curLine = lines.get(0).split(" ");
        instance.R = Integer.parseInt(curLine[0]);
        instance.C = Integer.parseInt(curLine[1]);
        instance.D = Integer.parseInt(curLine[2]);
        instance.T = Integer.parseInt(curLine[3]);
        instance.capacity = Integer.parseInt(curLine[4]);

        // Product weights
        curLine = lines.get(1).split(" ");
        instance.P = Integer.parseInt(curLine[0]);
        curLine = lines.get(2).split(" ");
        instance.weights = new int[curLine.length];
        for (int i = 0; i < curLine.length; i++) {
            instance.weights[i] = Integer.parseInt(curLine[i]);
        }

        // Warehouses
        offset = 4;
        curLine = lines.get(3).split(" ");
        int nW = Integer.parseInt(curLine[0]);
        instance.warehouses = new Warehouse[nW];

        for (int i = 0; i < nW; i++) {
            curLine = lines.get(offset + (2 * i)).split(" ");
            int r = Integer.parseInt(curLine[0]);
            int c = Integer.parseInt(curLine[1]);

            curLine = lines.get(offset + (2 * i) + 1).split(" ");
            int[] p = new int[instance.P];

            for (int j = 0; j < instance.P; j++) {
                p[j] = Integer.parseInt(curLine[j]);
            }

            instance.warehouses[i] = new Warehouse(r, c, p);
        }

        // Orders
        offset = 4 + (2 * nW);
        curLine = lines.get(offset).split(" ");
        int nO = Integer.parseInt(curLine[0]);
        instance.orders = new Order[nO];

        for (int i = 0; i < nO; i++) {
            curLine = lines.get(offset + 1 + (3 * i)).split(" ");
            int r = Integer.parseInt(curLine[0]);
            int c = Integer.parseInt(curLine[1]);

            curLine = lines.get(offset + 1 + (3 * i) + 2).split(" ");
            int[] p = new int[instance.P];

            for (int j = 0; j < curLine.length; j++) {
                p[Integer.parseInt(curLine[j])]++;
            }

            instance.orders[i] = new Order(r, c, p);
        }

        // Drones
        instance.drones = new Drone[instance.D];
        for (int i = 0; i < instance.drones.length; i++){
            instance.drones[i] = new Drone(instance.weights, instance.capacity);
        }
        return instance;
    }
}
