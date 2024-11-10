package com.lld.Module;

import com.lld.Module.Dispatcher.ExternalButtonDispatcher.ExternalEvenOddDispatcher;
import com.lld.Module.Dispatcher.ExternalButtonDispatcher.ExternalButtonDispatcher;
import com.lld.Module.Dispatcher.InternalButtonDispatcher.InternalButtonDispatcher;
import com.lld.Module.Dispatcher.InternalButtonDispatcher.InternalEvenOddDispatcher;
import com.lld.Module.Floor.Floor;
import com.lld.Module.Lift.LiftController;

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
            if(i < numberOfLifts)
                liftControllers.add(new LiftController(i, internalButtonDispatcher));
            floors.add(i, new Floor(externalButtonDispatcher, i));
        }
    }
    private void requestForLiftWithPrint(int source, int destination){
        System.out.println("Lift requested from floor " + source + " to go to floor " + destination);
        floors.get(source).pressExternalButtonForLift(destination);
        System.out.println("!! Destination Reached !! \n");
    }

    private void liftInternalRequestToReachWithPrint(int liftId, int destination){
        System.out.println("Lift " + liftId + " internal request to reach destination floor " + destination);
        liftControllers.get(liftId).getLift().pressInternalButton(destination);
        System.out.println("!! Destination Reached !! \n");
    }

    public void runSimulation(){
        requestForLiftWithPrint(2, 8);
        requestForLiftWithPrint(6, 20);
        liftInternalRequestToReachWithPrint(0, 10);
        liftInternalRequestToReachWithPrint(0, 14);
        liftInternalRequestToReachWithPrint(0, 2);
        liftInternalRequestToReachWithPrint(1, 16);
        liftInternalRequestToReachWithPrint(1, 3);

        requestForLiftWithPrint(9, 4);
        requestForLiftWithPrint(14, 8);
        requestForLiftWithPrint(2, 30);
    }
}
