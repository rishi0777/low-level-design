package com.example.LLD;

import com.example.LLD.Module.ParkingArea;
import com.example.LLD.Module.Ticket;
import com.example.LLD.Module.Vehicle.Vehicle;
import com.example.LLD.Module.Vehicle.VehicleFactory;
import com.example.LLD.Util.Enum.EntityType;
import com.example.LLD.Util.Enum.VehicleType;

import java.util.Scanner;

public class ParkingSystem {
    ParkingArea parkingArea;
    VehicleFactory vehicleFactory;

    void initialize(){
        boolean parkingAreaInitialized = initializeParkingArea();
        while(!parkingAreaInitialized){
            System.out.println("Parking Area should contain at-least 1 entry, 1 exit and 1 parking spot");
            System.out.println("Please re-initialize the parking area");
            parkingAreaInitialized = initializeParkingArea();
        }
        vehicleFactory = new VehicleFactory();
    }

    boolean initializeParkingArea(){
        System.out.println("Enter the 2D dimension for ParkingArea");
        Scanner sc= new Scanner(System.in);
        int length=sc.nextInt();
        int width=sc.nextInt();
        parkingArea = new ParkingArea(length,width);

        System.out.println("Provide cell values");
        System.out.println("0 for ParkingSpot");
        System.out.println("1 for EntryGate");
        System.out.println("-1 for ExitGate");

        for(int i=0;i<length;i++){
            for(int j=0;j<width;j++){
                int input = sc.nextInt();
                switch (input) {
                    case 0:
                        parkingArea.assignCellValue(i, j, EntityType.ParkingSpot);
                        break;
                    case 1:
                        parkingArea.assignCellValue(i, j, EntityType.EntryGate);
                        break;
                    case -1:
                        parkingArea.assignCellValue(i, j, EntityType.ExitGate);
                        break;
                    default:
                        System.out.println("Input is not valid. should be either 0, -1 or 1");
                        System.out.println("Assigning cell value as ParkingSpot as default");
                        parkingArea.assignCellValue(i, j, EntityType.ParkingSpot);
                }
            }
        }
        return parkingArea.isParkingAreaValid();
    }

    void run(){
        Vehicle scooty = vehicleFactory.getVehicle(VehicleType.TWO_WHEELER,"UP32 KX2030");
        Ticket ticket1 = parkingArea.park(scooty,0);
        Ticket ticket2 = parkingArea.park(vehicleFactory.getVehicle(VehicleType.FOUR_WHEELER,"UP32 TZ2030"),0);
        Ticket ticket3 = parkingArea.park(vehicleFactory.getVehicle(VehicleType.TWO_WHEELER,"UP32 GH2030"),1);
        Ticket ticket4 = parkingArea.park(vehicleFactory.getVehicle(VehicleType.FOUR_WHEELER,"UP32 WQ2030"),1);

        if(ticket1 != null)
            parkingArea.exit(ticket1,0);
        if(ticket2 != null)
            parkingArea.exit(ticket2,0);
        if(ticket3 != null)
            parkingArea.exit(ticket3,1);
        if(ticket4 != null)
            parkingArea.exit(ticket4,1);
    }
}
