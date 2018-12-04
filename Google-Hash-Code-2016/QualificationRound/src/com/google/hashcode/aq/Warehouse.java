package com.google.hashcode.aq;

public class Warehouse {
	private int r;
	private int c;
	private int[] products;

    public Warehouse(int r, int c, int[] products) {
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

    public void load(int type, int amount) {
		if (products[type] < amount) {
			throw new IllegalArgumentException();
		} else {
			products[type] -= amount;
		}
	}

    public void unload(int type, int amount) {
        products[type] += amount;
    }


    public boolean isEmpty() {
        for (int i = 0; i < products.length; i++) {
            if (products[i] != 0) return false;
        }
        return true;
    }

    public int maxProduct() {
        int max = 0;
        int index = 0;

        for (int i = 0; i < products.length; i++) {
            if (products[i] > max) {
                max = products[i];
                index = i;
            }
        }
        return index;
    }
}
