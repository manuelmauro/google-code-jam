package com.google.hashcode.aq;

public class ProblemInstance {
	public int R;
	public int C;
	public int T;
	public int P;
	public int D;
	public int capacity;
	
	public int[] weights;
	public Warehouse[] warehouses;
	public Order[] orders;
	public Drone[] drones;

	public ProblemInstance() {
		weights = new int[0];
		warehouses = new Warehouse[0];
		orders = new Order[0];
        drones = new Drone[0];
	}
}
