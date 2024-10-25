package com.example.LLD.Module.Vehicle;

import com.example.LLD.Util.Enum.VehicleType;

public class VehicleFactory {
    public Vehicle getVehicle(VehicleType vehicleType, String vehicleNumber){
        switch (vehicleType){
            case VehicleType.FOUR_WHEELER:
                return new FourWheeler(vehicleNumber);
            case VehicleType.TWO_WHEELER:
                return new TwoWheeler(vehicleNumber);
            default: return null;
        }
    }
}
