package com.google.hashcode.aq;

/**
 * Created by manuel on 13/02/16.
 */
public class Command {
    int drone;
    CommandType type;
    int warehouse;
    int order;
    int pType;
    int pAmount;
    int time;

    private Command(){};

    private Command(int drone, CommandType type, int warehouse, int order, int pType, int pAmount, int time) {
        this.drone = drone;
        this.type = type;
        this.warehouse = warehouse;
        this.order = order;
        this.pType = pType;
        this.pAmount = pAmount;
        this.time = time;
    }

    public static Command Deliver(int drone, int order, int pType, int pAmount) {
        return new Command(drone, CommandType.DELIVER, 0, order, pType, pAmount, 0);
    }

    public static Command Load(int drone, int warehouse, int pType, int pAmount) {
        return new Command(drone, CommandType.LOAD, warehouse, 0, pType, pAmount, 0);
    }

    public static Command Unload(int drone, int warehouse, int pType, int pAmount) {
        return new Command(drone, CommandType.UNLOAD, warehouse, 0, pType, pAmount, 0);
    }

    public static Command Wait(int drone, int time) {
        return new Command(drone, CommandType.WAIT, 0, 0, 0, 0, time);
    }

    @Override
    public String toString() {
        switch (type) {
            case DELIVER:
                return drone + " " + type.label() + " " + order + " " + pType + " " + pAmount;
            case LOAD:
            case UNLOAD:
                return drone + " " + type.label() + " " + warehouse + " " + pType + " " + pAmount;
            case WAIT:
                return drone + " " + type.label() + " " + time;
        }
        return "";
    }
}
