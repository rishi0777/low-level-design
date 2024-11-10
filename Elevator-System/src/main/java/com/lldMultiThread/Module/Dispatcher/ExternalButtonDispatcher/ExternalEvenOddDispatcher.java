package com.lldMultiThread.Module.Dispatcher.ExternalButtonDispatcher;

import com.lldMultiThread.Module.Lift.LiftController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExternalEvenOddDispatcher implements ExternalButtonDispatcher{
    private List<LiftController> liftControllers;
    private Map<Integer,Integer> floorServedByLiftController;

    public ExternalEvenOddDispatcher(int numberOfFloors, int numberOfLifts, List<LiftController> liftControllers){
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
    public void submitRequestForLiftAndDestination(int liftRequestedFloor, int destinationFloor) {
        if(!(liftRequestedFloor % 2 == 0 && destinationFloor % 2 == 0) &&
                !(liftRequestedFloor % 2 == 1 && destinationFloor % 2 == 1)) {
            // switch lift if source is even and destination is odd or vice-versa not possible
            System.out.println("ERROR: Request cannot be processed - odd to even floor request or vice-versa");
        } else {
            LiftController servingLiftController = liftControllers.get(floorServedByLiftController.get(liftRequestedFloor));
            servingLiftController.acceptNewRequest(liftRequestedFloor, destinationFloor);
        }
    }

    @Override
    public void submitRequestForDestination(int startingFloor, int destinationFloor) {
        LiftController servingLiftController = liftControllers.get(floorServedByLiftController.get(destinationFloor));
        servingLiftController.acceptNewRequest(destinationFloor);
    }
}
