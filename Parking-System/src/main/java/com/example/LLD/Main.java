package com.example.LLD;

public class Main {
    public static void main(String[] args) {
        System.out.println("----- Running the code -----\n");
        ParkingSystem parkingSystem = new ParkingSystem();
        parkingSystem.initialize();
        parkingSystem.run();
        System.out.println("----- Code Exited -----\n");
    }
}
