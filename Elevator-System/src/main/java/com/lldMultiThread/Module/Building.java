package com.lldMultiThread.Module;

import com.lldMultiThread.Module.Dispatcher.ExternalButtonDispatcher.ExternalEvenOddDispatcher;
import com.lldMultiThread.Module.Dispatcher.ExternalButtonDispatcher.ExternalButtonDispatcher;
import com.lldMultiThread.Module.Dispatcher.InternalButtonDispatcher.InternalButtonDispatcher;
import com.lldMultiThread.Module.Dispatcher.InternalButtonDispatcher.InternalEvenOddDispatcher;
import com.lldMultiThread.Module.Floor.Floor;
import com.lldMultiThread.Module.Lift.LiftController;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private List<Floor> floors;
    private List<LiftController> liftControllers;

    public Building(int numberOfFloors, int numberOfLifts){
        initialize(numberOfFloors, numberOfLifts);
    }

    private void initialize(int numberOfFloors, int numberOfLifts){
        liftControllers = new ArrayList<>();
        floors = new ArrayList<>(numberOfFloors);

        ExternalButtonDispatcher externalButtonDispatcher = new ExternalEvenOddDispatcher(numberOfFloors, numberOfLifts, liftControllers);
        InternalButtonDispatcher internalButtonDispatcher = new InternalEvenOddDispatcher(numberOfFloors, numberOfLifts, liftControllers);

        for(int i = 0; i < numberOfFloors; i++){
            if(i < numberOfLifts) {
                LiftController liftController = new LiftController(i, internalButtonDispatcher);
                Thread liftControllerThread = new Thread(liftController);
                liftControllerThread.start();
                liftControllers.add(liftController);
            }
            floors.add(i, new Floor(externalButtonDispatcher, i));
        }
    }
    private void liftExternalRequestToReachWithPrint(int source, int destination){
        System.out.println("EXTERNAL REQUEST -- from floor " + source + " to go to floor " + destination);
        floors.get(source).pressExternalButtonForLift(destination);
    }

    private void liftInternalRequestToReachWithPrint(int liftId, int destination){
        System.out.println("INTERNAL REQUEST -- from Lift " + liftId + " to go to floor " + destination);
        liftControllers.get(liftId).getLift().pressInternalButton(destination);
    }

    synchronized public void runSimulation(){
        try {
            liftExternalRequestToReachWithPrint(6, 12);
            liftInternalRequestToReachWithPrint(0, 10);
            wait(10000);
            liftInternalRequestToReachWithPrint(0, 14);
            liftExternalRequestToReachWithPrint(6, 24);
            liftExternalRequestToReachWithPrint(6, 10);
            liftExternalRequestToReachWithPrint(6, 2);
            liftInternalRequestToReachWithPrint(0, 14);
            wait(4000);
            liftInternalRequestToReachWithPrint(0, 24);
            liftInternalRequestToReachWithPrint(0, 5);
            wait(3000);
            liftInternalRequestToReachWithPrint(0, 12);

            liftExternalRequestToReachWithPrint(1, 5) ;
            liftExternalRequestToReachWithPrint(19, 7);
            liftExternalRequestToReachWithPrint(19, 29);
            liftInternalRequestToReachWithPrint(1, 15);
            liftExternalRequestToReachWithPrint(8, 4);
            liftExternalRequestToReachWithPrint(8, 4);
            liftExternalRequestToReachWithPrint(14, 24);
        } catch (Exception e){
            System.out.println("Exception Occurred " + e.getMessage());
        }
    }
}
