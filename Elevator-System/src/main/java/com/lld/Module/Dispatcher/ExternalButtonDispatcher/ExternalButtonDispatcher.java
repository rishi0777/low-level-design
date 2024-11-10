package com.lld.Module.Dispatcher.ExternalButtonDispatcher;

public interface ExternalButtonDispatcher {
    void submitRequestForLift (int liftRequestedFloor, int destinationFloor);
    void submitRequestForDestination(int startingFloor, int destinationFloor);
}
