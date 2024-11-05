package com.lld;


import com.lld.Module.Building;

public class ElevatorSystemApplication {
	public static void main(String[] args) {
		Building building = new Building(40,4);
		building.runSimulation();
	}
}
