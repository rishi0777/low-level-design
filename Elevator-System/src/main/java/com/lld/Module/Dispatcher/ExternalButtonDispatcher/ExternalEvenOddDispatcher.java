package com.lld.Module.Dispatcher.ExternalButtonDispatcher;

import com.lld.Module.Lift.LiftController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExternalEvenOddDispatcher implements ExternalButtonDispatcher{
    private List<LiftController> liftControllers;
    private Map<Integer,Integer> floorServedByLiftController;

    public ExternalEvenOddDispatcher(int numberOfFloors, int numberOfLifts, List<LiftController> liftControllers){
        // considering only 3 lifts are there
        // lift 0 for odd floors
        // lift 1 for even floors
        // lift 2 for if user wants to go from odd to even or even to odd floor
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
    public void submitRequestForLift(int liftRequestedFloor, int destinationFloor) {
        LiftController servingLiftController = liftControllers.get(floorServedByLiftController.get(liftRequestedFloor));
        servingLiftController.acceptNewRequest(liftRequestedFloor);
        submitRequestForDestination(liftRequestedFloor, destinationFloor);
    }

    @Override
    public void submitRequestForDestination(int startingFloor, int destinationFloor) {
        // switch lift if source is even and destination is odd or vice-versa
        if(!(startingFloor % 2 == 0 && destinationFloor % 2 == 0) &&
                !(startingFloor % 2 == 1 && destinationFloor % 2 == 1)) {
            // this controller moves lift from odd to even or even to odd which act as mediator for switching
            // left 3 serves only two floors 19 and 20 ... first and second median of total floors
            System.out.println(" -- No direct path, first go to floor " + (startingFloor % 2 == 0 ? "20" : "19") +
                    " then change lift to go to " + (destinationFloor % 2 == 0 ? "20" : "19") +
                    " then change lift go to " + destinationFloor + " --");
            LiftController servingLiftController = liftControllers.get(floorServedByLiftController.get(startingFloor));
            LiftController evenOddLiftController = liftControllers.get(2);
            if (startingFloor % 2 == 0) {
                servingLiftController.acceptNewRequest(20);
                evenOddLiftController.acceptNewRequest(20);
            }
            else {
                servingLiftController.acceptNewRequest(19);
                evenOddLiftController.acceptNewRequest(19);
            }
            System.out.println("-- Changing lift and moving from lift " + floorServedByLiftController.get(startingFloor) + " to lift 2 --");

            servingLiftController = liftControllers.get(floorServedByLiftController.get(destinationFloor));
            if (destinationFloor % 2 == 0) {
                evenOddLiftController.acceptNewRequest(20);
                servingLiftController.acceptNewRequest(20);
            }
            else {
                evenOddLiftController.acceptNewRequest(19);
                servingLiftController.acceptNewRequest(19);
            }
            System.out.println("-- Changing lift and moving from lift 2 to lift " + floorServedByLiftController.get(destinationFloor) + " --");
        }
        LiftController servingLiftController = liftControllers.get(floorServedByLiftController.get(destinationFloor));
        servingLiftController.acceptNewRequest(destinationFloor);
    }
}
