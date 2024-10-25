package com.example.LLD.Module.Entities;

import com.example.LLD.Module.Vehicle.Vehicle;
import com.example.LLD.Util.Enum.EntityType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExitGate extends Entity {
    public ExitGate(int x, int y){
        super(x,y,EntityType.ExitGate);
    }

    public void updateParkingSpot(ParkingSpot parkingSpot, boolean isParkingSpotEmpty){
        parkingSpot.setEmpty(isParkingSpotEmpty);
    }

    public void computeBill(long entryTime, Vehicle vehicle){
        long exitTime = new Date().getTime();
        long parkedTime = exitTime - entryTime;
        long differenceInHours = parkedTime / (1000 * 60 * 60);
        long cost = (differenceInHours + 1) * vehicle.getCostPerHour();
        generateBill(differenceInHours,cost);
    }

    private void generateBill(long parkedTime, long cost){
        System.out.println("--------- Parking Invoice ---------");
        System.out.println("Total Time (In Hours): " + parkedTime);
        System.out.println("Total Cost: " + cost);
        System.out.println("-----------------------------------");
    }
}
