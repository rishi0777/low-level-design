package com.lld.Module.Floor;

import com.lld.Module.Dispatcher.ExternalButtonDispatcher.ExternalButtonDispatcher;

public class ExternalButton {
    private ExternalButtonDispatcher externalButtonDispatcher;

    public ExternalButton(ExternalButtonDispatcher externalButtonDispatcher){
        this.externalButtonDispatcher = externalButtonDispatcher;
    }

    void pressButton(int liftRequestedFloor, int destinationFloor){
        externalButtonDispatcher.submitRequestForLift(liftRequestedFloor, destinationFloor);
    }
}
