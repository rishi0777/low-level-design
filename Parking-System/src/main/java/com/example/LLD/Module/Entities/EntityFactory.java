package com.example.LLD.Module.Entities;

import com.example.LLD.Util.Enum.EntityType;

public class EntityFactory {
    public Entity getEntity(int x,int y,EntityType entityType){
        switch (entityType){
            case EntityType.EntryGate: return new EntryGate(x,y);
            case EntityType.ParkingSpot: return new ParkingSpot(x,y);
            case EntityType.ExitGate: return new ExitGate(x,y);
        }
        return null;
    }
}
