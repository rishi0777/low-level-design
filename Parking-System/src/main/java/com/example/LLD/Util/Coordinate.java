package com.example.LLD.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Coordinate<T> {

    private List<T> cords;

    public Coordinate(T x, T y) {
        cords = new ArrayList<>();
        cords.addAll(Arrays.asList(x,y));
    }

    public List<T> getCoordinates(){
        return cords;
    }
}