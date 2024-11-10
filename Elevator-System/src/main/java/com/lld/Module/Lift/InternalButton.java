package com.lld.Module.Lift;

import com.lld.Module.Dispatcher.InternalButtonDispatcher.InternalButtonDispatcher;

public class InternalButton {
    private InternalButtonDispatcher internalButtonDispatcher;

    public InternalButton(InternalButtonDispatcher internalButtonDispatcher){
        this.internalButtonDispatcher = internalButtonDispatcher;
    }

    void pressButton(int liftId, int destinationFloor){
        internalButtonDispatcher.submitRequestForDestination(liftId, destinationFloor);
    }

}
