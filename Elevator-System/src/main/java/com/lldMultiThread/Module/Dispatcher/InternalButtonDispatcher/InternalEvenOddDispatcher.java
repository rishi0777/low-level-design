package com.lldMultiThread.Module.Dispatcher.InternalButtonDispatcher;

import com.lldMultiThread.Module.Lift.LiftController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InternalEvenOddDispatcher implements InternalButtonDispatcher{
    private List<LiftController> liftControllers;
    private Map<Integer,Integer> floorServedByLiftController;

    public InternalEvenOddDispatcher(int numberOfFloors, int numberOfLifts, List<LiftController> liftControllers){
        // considering only 2 lifts are there
        // lift 0 for odd floors
        // lift 1 for even floors
        this.liftControllers = liftControllers;
        floorServedByLiftController = new HashMap<>();
        for(int i = 0; i < numberOfFloors; i++) {
            if (i % 2 != 0)
                floorServedByLiftController.put(i + 1, 0);
            else
                floorServedByLiftController.put(i + 1, 1);
        }
    }

    @Override
    public void submitRequestForDestination(int liftId, int destinationFloor) {
        LiftController servingLiftController = liftControllers.get(floorServedByLiftController.get(destinationFloor));
        if(liftId != servingLiftController.getLift().getLiftId()){
            System.out.println("-- Lift " + liftId + " does not contain floor " + destinationFloor + " as an internal button inside it. Even floors are served by lift 0 and Odd floors are served by lift 1 --");
        } else {
            servingLiftController.acceptNewRequest(destinationFloor);
        }
    }
}
