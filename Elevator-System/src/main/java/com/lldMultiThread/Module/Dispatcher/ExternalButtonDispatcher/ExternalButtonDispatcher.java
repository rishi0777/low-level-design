package com.lldMultiThread.Module.Dispatcher.ExternalButtonDispatcher;

public interface ExternalButtonDispatcher {
    void submitRequestForLiftAndDestination (int liftRequestedFloor, int destinationFloor);
    void submitRequestForDestination(int startingFloor, int destinationFloor);
}
