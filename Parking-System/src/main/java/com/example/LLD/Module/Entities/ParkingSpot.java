package com.example.LLD.Module.Entities;

import com.example.LLD.Util.Enum.EntityType;
import com.example.LLD.Util.Enum.VehicleType;

public class ParkingSpot extends Entity {
    private boolean isEmpty;
    private VehicleType parkingSpotType;

    public ParkingSpot(int x, int y){
        super(x,y,EntityType.ParkingSpot);
        isEmpty = true;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
