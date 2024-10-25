package com.example.LLD.Module.Entities;

import com.example.LLD.Util.Coordinate;
import com.example.LLD.Util.Enum.EntityType;

import java.util.List;

public class Entity {
    private Coordinate<Integer> cords;
    private EntityType entityType;

    public Entity(int x,int y,EntityType entityType){
        this.cords = new Coordinate<>(x,y);
        this.entityType = entityType;
    }

    public List<Integer> getCords(){
        return cords.getCoordinates();
    }

    public EntityType getEntityType() {
        return entityType;
    }
}
