package com.example.LLD.Module;

import com.example.LLD.Module.Entities.ParkingSpot;
import com.example.LLD.Module.Vehicle.Vehicle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket {
    private long time;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;

    public Ticket(Vehicle vehicle,ParkingSpot parkingSpot){
        this.time = new Date().getTime();
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
    }

    public void printTicket(){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.println("------ Generated Ticket ------");
        System.out.println("Vehicle Number: " + vehicle.getVehicleNumber());
        System.out.println("Vehicle Type: " + vehicle.getVehicleType());
        System.out.println("Parking Spot:  (" + parkingSpot.getCords().getFirst() + "," + parkingSpot.getCords().get(1) + ")");
        System.out.println("Ticket Creation Time: " + timeFormat.format(time));
        System.out.println("------------------------------");
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public long getTime() {
        return time;
    }
}
