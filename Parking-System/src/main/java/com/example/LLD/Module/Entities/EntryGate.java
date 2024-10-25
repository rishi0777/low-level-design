package com.example.LLD.Module.Entities;

import com.example.LLD.Module.Ticket;
import com.example.LLD.Module.Vehicle.Vehicle;
import com.example.LLD.Util.Coordinate;
import com.example.LLD.Util.Enum.EntityType;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EntryGate extends Entity{

    public EntryGate(int x, int y){
        super(x,y,EntityType.EntryGate);
    }

    public ParkingSpot findNearestAvailableParkingSpot(List<List<Entity>> parkingArea) {
        int length = parkingArea.size();
        int width = parkingArea.get(0).size();
        Queue<Coordinate<Integer>> queue = new LinkedList<>();
        queue.add(new Coordinate<>(getCords().get(0), getCords().get(1)));

        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            Coordinate<Integer> top = queue.peek();
            queue.poll();

            for (int i = 0; i < levelSize; i++) {
                for (int j = 0; j < 4; j++) {
                    int newX = top.getCoordinates().get(0) + dx[j];
                    int newY = top.getCoordinates().get(1) + dy[j];
                    if (newX >= 0 && newX < length && newY >= 0 && newY < width) {
                        if (parkingArea.get(newX).get(newY).getEntityType().equals(EntityType.ParkingSpot)) {
                            ParkingSpot parkingSpot = (ParkingSpot) parkingArea.get(newX).get(newY);
                            if (parkingSpot.isEmpty()) {
                                return parkingSpot;
                            }
                        }
                        queue.add(new Coordinate<>(newX, newY));
                    }
                }
            }
        }

        return null;
    }

    public void updateParkingSpot(ParkingSpot parkingSpot, boolean isParkingSpotEmpty){
        parkingSpot.setEmpty(isParkingSpotEmpty);
    }

    public Ticket generateTicket(Vehicle vehicle, ParkingSpot parkingSpot){
        return new Ticket(vehicle, parkingSpot);
    }
}
