package com.lld.Module.Floor;

import com.lld.Module.Dispatcher.ExternalButtonDispatcher.ExternalButtonDispatcher;
import com.lld.Utils.Enum.Direction;

import java.util.List;

public class ExternalButton {
    private ExternalButtonDispatcher externalButtonDispatcher;

    public ExternalButton(ExternalButtonDispatcher externalButtonDispatcher){
        this.externalButtonDispatcher = externalButtonDispatcher;
    }

    void pressButton(int liftRequestedFloor, int destinationFloor){
        externalButtonDispatcher.submitRequestForLiftAndDestination(liftRequestedFloor, destinationFloor);
    }
}
