package com.example.LLD.Module;

import com.example.LLD.Module.Entities.*;
import com.example.LLD.Module.Vehicle.Vehicle;
import com.example.LLD.Util.Coordinate;
import com.example.LLD.Util.Enum.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ParkingArea {
    private int totalEntryGates, totalExitGates;
    private int length, width;
    private List<List<Entity>> parkingArea;
    private EntityFactory entityFactory;
    private HashMap<Integer, Entity> entryGateMap;
    private HashMap<Integer, Entity> exitGateMap;

    public ParkingArea(int length, int width) {
        this.length = length;
        this.width = width;
        this.totalEntryGates = 0;
        this.totalExitGates= 0;
        entryGateMap = new HashMap<>();
        exitGateMap = new HashMap<>();
        parkingArea = new ArrayList<>(length);
        entityFactory = new EntityFactory();

        for (int i = 0; i < length; i++) {
            List<Entity> row = new ArrayList<>(width);
            for (int j = 0; j < width; j++) {
                row.add(null);
            }
            parkingArea.add(row);
        }
    }

    public List<Integer> getBoundary() {
        return Arrays.asList(length, width);
    }

    public Entity getParkingAreaCell(Coordinate<Integer> cell) {
        return parkingArea.get(cell.getCoordinates().get(0)).get(cell.getCoordinates().get(1));
    }

    public void assignCellValue(int x, int y, EntityType entityType) {
        if (x >= 0 && x < length && y >= 0 && y < width) {
            Entity entity = entityFactory.getEntity(x, y, entityType);
            parkingArea.get(x).set(y, entity);
            switch (entityType) {
                case EntityType.EntryGate:
                    entryGateMap.put(totalEntryGates++, entity);
                    break;
                case EntityType.ExitGate:
                    exitGateMap.put(totalExitGates++, entity);
                    break;
            }
        } else {
            System.out.println("Invalid cell value it should lie in provided area");
        }
    }

    public boolean isParkingAreaValid() {
        int entry = 0, exit = 0, parkingSpot = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                switch (parkingArea.get(i).get(j).getEntityType()) {
                    case EntityType.ParkingSpot -> parkingSpot += 1;
                    case EntityType.EntryGate -> entry += 1;
                    case EntityType.ExitGate -> exit += 1;
                }
            }
        }
        return exit >= 1 && entry >= 1 && parkingSpot >= 1;
    }

    public Ticket park(Vehicle vehicle, int entryGateNumber) {
        EntryGate entryGate = (EntryGate) entryGateMap.getOrDefault(entryGateNumber, null);
        if (entryGate != null) {
            ParkingSpot parkingSpot = entryGate.findNearestAvailableParkingSpot(parkingArea);
            if (parkingSpot != null) {
                entryGate.updateParkingSpot(parkingSpot, false);
                Ticket ticket = entryGate.generateTicket(vehicle, parkingSpot);
                ticket.printTicket();
                return ticket;
            } else {
                System.out.println("Parking spots not available, please come after sometime.");
            }
        } else {
            System.out.println("EntryGate does not exist");
        }
        return null;
    }

    public void exit(Ticket ticket, int exitGateNumber) {
        ExitGate exitGate = (ExitGate) exitGateMap.getOrDefault(exitGateNumber, null);

        if (exitGate != null) {
            exitGate.computeBill(ticket.getTime(), ticket.getVehicle());
            // After payment is successful update parking spot
            exitGate.updateParkingSpot(ticket.getParkingSpot(), true);
        } else {
            System.out.println("ExitGate does not exist");
        }
    }
}
