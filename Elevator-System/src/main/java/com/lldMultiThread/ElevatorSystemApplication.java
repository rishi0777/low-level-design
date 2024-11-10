package com.lldMultiThread;

import com.lldMultiThread.Module.Building;

public class ElevatorSystemApplication {
	public static void main(String[] args) {
		Building building = new Building(40,4);
		building.runSimulation();
	}
}
