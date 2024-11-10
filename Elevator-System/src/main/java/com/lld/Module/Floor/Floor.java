package com.lld.Module.Floor;

import com.lld.Module.Dispatcher.ExternalButtonDispatcher.ExternalButtonDispatcher;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Floor {
    private int currentFloor;
    private ExternalButton externalButton;

    public Floor(ExternalButtonDispatcher externalButtonDispatcher, int currentFloor){
        this.currentFloor = currentFloor;
        this.externalButton = new ExternalButton(externalButtonDispatcher);
    }
    public void pressExternalButtonForLift(int destinationFloor){
        externalButton.pressButton(currentFloor, destinationFloor);
    }
}
