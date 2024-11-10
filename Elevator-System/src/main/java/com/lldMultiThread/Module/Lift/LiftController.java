package com.lldMultiThread.Module.Lift;

import com.lldMultiThread.Module.Dispatcher.InternalButtonDispatcher.InternalButtonDispatcher;
import com.lldMultiThread.Utils.Enum.Direction;
import com.lldMultiThread.Utils.Enum.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class LiftController implements Runnable{
    private Lift lift;
    private Queue<Integer> pending;
    private PriorityQueue<Integer> downQueue; // MaxPriorityQueue
    private PriorityQueue<Integer> upQueue; // MinPriorityQueue
    private Map<Integer, List<Integer>> liftReachedDestinationFloor;

    public LiftController(int liftId, InternalButtonDispatcher internalButtonDispatcher){
        pending = new LinkedList<>();
        upQueue = new PriorityQueue<>();
        liftReachedDestinationFloor = new HashMap<>();
        downQueue = new PriorityQueue<>(Collections.reverseOrder());
        lift = new Lift(liftId, internalButtonDispatcher);
    }

    synchronized private void updateResourceWithRequest(int requestedFloor, Direction direction){
        if(lift.getDirection() == direction){
            // System.out.println("Up " + requestedFloor + " " + lift.getCurrentFloor()); // not required
            if(requestedFloor > lift.getCurrentFloor()){
                upQueue.add(requestedFloor);
            } else if(requestedFloor < lift.getCurrentFloor()){ // floor already passed will be covered in next iteration when lift goes from up to down
                pending.add(requestedFloor);
            } else {
                System.out.println("!! LIFT " + lift.getLiftId() + " is already at destination floor " + requestedFloor + " !! \n");
            }
        } else {
            // System.out.println("Down " + requestedFloor + " " + lift.getCurrentFloor()); // not required
            if(requestedFloor < lift.getCurrentFloor()){
                downQueue.add(requestedFloor);
            } else if(requestedFloor > lift.getCurrentFloor()){ // floor already passed will be covered in next iteration when lift goes from down to up
                pending.add(requestedFloor);
            } else {
                System.out.println("!! LIFT " + lift.getLiftId() + " is already at destination floor " + requestedFloor + " !! \n");
            }
        }
    }

    synchronized public void acceptNewRequest(int destinationFloor){
        updateResourceWithRequest(destinationFloor, Direction.UP);

        if(lift.getStatus() == Status.IDEAL) {
            lift.setStatus(Status.MOVING);
            notify();
        }
    }

    synchronized public void acceptNewRequest(int liftRequestedFloor, int destinationFloor){
        Direction direction = lift.getCurrentFloor() <= liftRequestedFloor ? Direction.UP : Direction.DOWN;
        updateResourceWithRequest(liftRequestedFloor, direction);

        if(liftRequestedFloor == lift.getCurrentFloor())
            updateResourceWithRequest(destinationFloor, direction);
        else {
            List<Integer> destinationFloorRequest = liftReachedDestinationFloor.getOrDefault(liftRequestedFloor, new ArrayList<>());
            destinationFloorRequest.add(destinationFloor);
            liftReachedDestinationFloor.put(liftRequestedFloor, destinationFloorRequest);
        }

        if(lift.getStatus() == Status.IDEAL) {
            lift.setStatus(Status.MOVING);
            notify();
        }
    }

    synchronized private void fillOtherUsingPendingQueue(boolean hasReachedTop) throws InterruptedException{
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

    synchronized private void liftReachedRaiseRequestForDestination(int reachedFloor){
        if(liftReachedDestinationFloor.containsKey(reachedFloor)){
            List<Integer> destinationFloorRequest = liftReachedDestinationFloor.getOrDefault(reachedFloor,new ArrayList<>());
            while(!destinationFloorRequest.isEmpty()){
                acceptNewRequest(destinationFloorRequest.get(0));
                destinationFloorRequest.remove(0);
            }
            liftReachedDestinationFloor.remove(reachedFloor);
        }
    }

    synchronized private void stopLift() throws InterruptedException{
        wait();
        moveLift();
    }

    synchronized private void moveLift() throws InterruptedException{
        while(!pending.isEmpty() || !upQueue.isEmpty() || !downQueue.isEmpty()) {
            int nextFloor = 0;
            int currentFloor = lift.getCurrentFloor();

            if(!upQueue.isEmpty() && lift.getDirection() == Direction.UP){
                int requestedFloor= upQueue.peek();
                if(requestedFloor == currentFloor){
                    liftReachedRaiseRequestForDestination(requestedFloor);
                    System.out.println("!! LIFT " + lift.getLiftId() + " reached destination - Floor " + currentFloor + " !! \n");
                    while(!upQueue.isEmpty() && currentFloor == upQueue.peek()) upQueue.poll();
                } else {
                    System.out.println("LIFT " + lift.getLiftId() + " MOVING UP: Lift reached floor " + currentFloor + " ");
                }
                if(!upQueue.isEmpty()) {
                    nextFloor = currentFloor + 2;
                    lift.setCurrentFloor(nextFloor);
                    wait(500);
                }
                // System.out.println(requestedFloor); // not required
            } else if(upQueue.isEmpty() && lift.getDirection() == Direction.UP) {
                lift.setDirection(Direction.DOWN);
                fillOtherUsingPendingQueue(true);
            } else if (!downQueue.isEmpty() && lift.getDirection() == Direction.DOWN){
                int requestedFloor= downQueue.peek();
                if(requestedFloor == currentFloor){
                    liftReachedRaiseRequestForDestination(requestedFloor);
                    System.out.println("!! LIFT " + lift.getLiftId() + " reached destination - Floor " + currentFloor + " !! \n");
                    while(!downQueue.isEmpty() && currentFloor == downQueue.peek()) downQueue.poll();
                } else {
                    System.out.println("LIFT " + lift.getLiftId() + " MOVING DOWN: Lift reached floor " + currentFloor);
                }
                if(!downQueue.isEmpty()) {
                    nextFloor = currentFloor - 2;
                    lift.setCurrentFloor(nextFloor);
                    wait(500);
                }
                // System.out.println(requestedFloor); // not required
            } else if(downQueue.isEmpty()) {
                lift.setDirection(Direction.UP);
                fillOtherUsingPendingQueue(false);
            }
        }
        lift.setStatus(Status.IDEAL);
        stopLift();
    }

    @Override
    public void run() {
        try {
            if(lift.getStatus() == Status.IDEAL){
                stopLift();
            } else {
                moveLift();
            }
        }
        catch (InterruptedException e) {
            System.out.println("Exception Occurred -->" + e.getMessage());
        }
    }

}
