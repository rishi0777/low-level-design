package com.lld.Module.Lift;

import com.lld.Module.Dispatcher.InternalButtonDispatcher.InternalButtonDispatcher;
import com.lld.Utils.Enum.Direction;
import com.lld.Utils.Enum.Status;

public class Lift{
    private int liftId;
    private Display display;
    private InternalButton internalButton;

    public Lift(int liftId, InternalButtonDispatcher internalButtonDispatcher){
        this.liftId = liftId;
        // initially all lifts will be ideal at 1 floor in there respective lift shaft/tunnel
        // they all will move in UP direction first when they start
        display = new Display(liftId == 2 ? 19 : liftId == 1 ? 1 : 0, Direction.UP);
        internalButton = new InternalButton(internalButtonDispatcher);
    }

    public void pressInternalButton(int destinationFloor){
        internalButton.pressButton(liftId, destinationFloor);
    }
    public int getLiftId(){
        return liftId;
    }

    public int getCurrentFloor(){
        return display.getCurrentFloor();
    }

    public void setCurrentFloor(int floor){
        display.setCurrentFloor(floor);
    }

    public Direction getDirection(){
        return display.getDirection();
    }

    public void setDirection(Direction direction){
        display.setDirection(direction);
    }

    public Status getStatus(){
       return display.getStatus();
    }

    public void setStatus(Status status){
        display.setStatus(status);
    }
}
