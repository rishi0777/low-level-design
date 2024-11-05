package com.lld.Module.Lift;

import com.lld.Utils.Enum.Direction;
import com.lld.Utils.Enum.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Display {
    private int currentFloor;
    private Status status;
    private Direction direction;

    public Display(int currentFloor, Direction direction){
        this.direction = direction;
        this.status = Status.IDEAL;
        this.currentFloor = currentFloor;
    }
}
