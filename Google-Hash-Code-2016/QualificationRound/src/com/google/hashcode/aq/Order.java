package com.google.hashcode.aq;

public class Order {
	private int r;
	private int c;
	private int[] products;

    public Order(int r, int c, int[] products) {
        this.r = r;
        this.c = c;
        this.products = products;
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

    public void deliver(int type, int amount) {
        if (products[type] < amount) {
            throw new IllegalArgumentException("More items than requested.");
        } else {
            products[type] -= amount;
        }
    }
}
