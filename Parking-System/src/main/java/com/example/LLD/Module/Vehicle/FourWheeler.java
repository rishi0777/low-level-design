package com.example.LLD.Module.Vehicle;

import com.example.LLD.Util.Enum.VehicleType;

public class FourWheeler extends Vehicle {
   public FourWheeler(String vehicleNumber){
       super(VehicleType.FOUR_WHEELER,50, vehicleNumber);
   }
}
