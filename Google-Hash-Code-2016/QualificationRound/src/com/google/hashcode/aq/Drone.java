package com.google.hashcode.aq;

public class Drone {
	private int r;
	private int c;
	private int[] products;
    private int[] weights;
	private int capacity;

	public Drone(int[] weights, int capacity) {
		r = 0;
		c = 0;
		products = new int[weights.length];
        this.weights = weights;
        this.capacity = capacity;
	}

    public int r() {
        return r;
    }

    public int c() {
        return c;
    }

    public int products(int type) {
        return products[type];
    }

    public boolean canLoad(int type, int amount) {
        return weights[type]*amount <= remainingCapacity();
    }

    public boolean canDeliver(int type, int amount){
        return products[type] >= amount;
    }

    public int load(int r, int c, int type, int amount) {
        if (!canLoad(type, amount)) {
            throw new IllegalArgumentException("Capacity exceeded.");
        }
        products[type] += amount;
        return move(r, c) + 1;
    }

    public int deliver(int r, int c, int type, int amount) {
        if (!canDeliver(type, amount)) {
            throw new IllegalArgumentException("Not enough products.");
        }
        products[type] -= amount;
        return move(r, c) + 1;
    }

    public int unload(int r, int c, int type, int amount) {
        if (!canDeliver(type, amount)) {
            throw new IllegalArgumentException("Not enough products.");
        }
        products[type] -= amount;
        return move(r, c) + 1;
    }

    public int remainingCapacity() {
        int load = 0;
        for (int i = 0; i < products.length; i++) {
            load += weights[i]*products[i];
        }
        return capacity - load;
    }

    public boolean isEmpty() {
        return remainingCapacity() == capacity;
    }

    private int move(int r, int c) {
        this.r = r;
        this.c = c;
        return HashCodeUtils.distance(this.r, this.c, r, c);
    }
}
