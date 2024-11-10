package com.lld.Module.Dispatcher.ExternalButtonDispatcher;

import com.lld.Module.Lift.LiftController;
import com.lld.Utils.Enum.Direction;

import java.util.List;

public interface ExternalButtonDispatcher {
    void submitRequestForLiftAndDestination (int liftRequestedFloor, int destinationFloor);
    void submitRequestForDestination(int startingFloor, int destinationFloor);
}
