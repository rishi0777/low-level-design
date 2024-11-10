package com.lld.Module.Lift;

import com.lld.Module.Dispatcher.InternalButtonDispatcher.InternalButtonDispatcher;
import com.lldMultiThread.Utils.Enum.Direction;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

@Getter
@Setter
public class LiftController {
    private Lift lift;
    private Queue<Integer> pending;
    private PriorityQueue<Integer> downQueue; // MaxPriorityQueue
    private PriorityQueue<Integer> upQueue; // MinPriorityQueue

    public LiftController(int liftId, InternalButtonDispatcher internalButtonDispatcher){
        pending = new LinkedList<>();
        upQueue = new PriorityQueue<>();
        lift = new Lift(liftId, internalButtonDispatcher);
        downQueue = new PriorityQueue<>(Collections.reverseOrder());
    }

    public void acceptNewRequest(int destinationFloor){
        if(lift.getDirection() == Direction.UP){
            if(destinationFloor > lift.getCurrentFloor()){
                upQueue.add(destinationFloor);
            } else if(destinationFloor < lift.getCurrentFloor()){
                // floor already passed will be covered in next iteration when lift goes from up to down
                pending.add(destinationFloor);
            } else {
                System.out.println("Lift reached the destination floor " + destinationFloor);
            }
        }
        controlLift();
    }

    // if lift has reached top then it will
    // go down, so we will fill downQueue else
    // lift has reached bottom then fill upQueue
    private void fillOtherUsingPendingQueue(boolean hasReachedTop){
        while(!pending.isEmpty()){
            int pendingFloorToBeTraversed = pending.peek();
            pending.poll();
            if(hasReachedTop){
                downQueue.add(pendingFloorToBeTraversed);
            } else {
                upQueue.add(pendingFloorToBeTraversed);
            }
        }
    }

    public void controlLift(){
        while(!pending.isEmpty() || !upQueue.isEmpty() || !downQueue.isEmpty()) {
            if (lift.getDirection() == Direction.UP) {
                int prevFloor = lift.getCurrentFloor();
                while(!upQueue.isEmpty()){
                    int curFloor = upQueue.peek();
                    int secondsElapsedToReachCurFloor = curFloor - prevFloor;
                    System.out.println("LIFT " + lift.getLiftId() + " MOVING UP: Seconds elapsed to move lift from " + prevFloor + " to " + curFloor + " is " + secondsElapsedToReachCurFloor);
                    lift.setCurrentFloor(curFloor);
                    upQueue.poll();
                }

                fillOtherUsingPendingQueue(true);

                prevFloor = lift.getCurrentFloor();
                while(!downQueue.isEmpty()){
                    int curFloor = downQueue.peek();
                    int secondsElapsedToReachCurFloor = prevFloor - curFloor; // since going down so previous floor will have greater value
                    System.out.println("LIFT " + lift.getLiftId() + " MOVING DOWN: Seconds elapsed to move lift from " + prevFloor + " to " + curFloor + " is " + secondsElapsedToReachCurFloor);
                    lift.setCurrentFloor(curFloor);
                    downQueue.poll();
                }

                fillOtherUsingPendingQueue(false); // reached bottom
            }
        }
    }
}
