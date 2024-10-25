package com.example.LLD.Module.Vehicle;

import com.example.LLD.Util.Enum.VehicleType;

public class Vehicle {
    private String vehicleNumber;
    private int costPerHour;
    private VehicleType vehicleType;

    public Vehicle(VehicleType vehicleType,int costPerHour,String vehicleNumber){
        this.vehicleType = vehicleType;
        this.costPerHour = costPerHour;
        this.vehicleNumber = vehicleNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public int getCostPerHour() {
        return costPerHour;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }
}
