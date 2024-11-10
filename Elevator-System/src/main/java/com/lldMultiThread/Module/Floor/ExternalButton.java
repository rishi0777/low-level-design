package com.lldMultiThread.Module.Floor;

import com.lldMultiThread.Module.Dispatcher.ExternalButtonDispatcher.ExternalButtonDispatcher;

public class ExternalButton {
    private ExternalButtonDispatcher externalButtonDispatcher;

    public ExternalButton(ExternalButtonDispatcher externalButtonDispatcher){
        this.externalButtonDispatcher = externalButtonDispatcher;
    }

    void pressButton(int liftRequestedFloor, int destinationFloor){
        externalButtonDispatcher.submitRequestForLiftAndDestination(liftRequestedFloor, destinationFloor);
    }
}
