package Uber.ParkingLot;

import java.util.List;
import java.util.Map;

public class ParkingLot {
    String name;
    private List<ParkingFloor> parkingFloors;
    private static ParkingLot parkingLot=null;

    private ParkingLot(String name,List<ParkingFloor> parkingFloors){
        this.name = name;
        this.parkingFloors= parkingFloors;
    }

    public static ParkingLot getInstance(String name,List<ParkingFloor> parkingFloors){
        if(parkingLot == null){
            parkingLot = new ParkingLot(name,parkingFloors);
        }
        return parkingLot;
    }

    public void addFloors(String name, Map<ParkingSlotType, Map<String,ParkingSlot>> parkSlots){
        ParkingFloor parkingFloor = new ParkingFloor(name,parkSlots);
        parkingFloors.add(parkingFloor);
    }

    public void removeFloors(ParkingFloor parkingFloor){
        parkingFloors.remove(parkingFloor);
    }

    public Ticket assignTicket(Vehicle vehicle){
        ParkingSlot parkingSlot = getParkingSlotForVehicleAndPark(vehicle);
        if(parkingSlot == null) return null;
        Ticket ticket = createTicketForSlot(vehicle,parkingSlot);
        return ticket;
    }

    public double scanAndPay(Ticket ticket){
        long endTime = System.currentTimeMillis();
        ticket.endTime = endTime;
        int duration = (int) (endTime-ticket.getStartTime())/1000;
        return ticket.parkingSlot.parkingSlotType.getPriceForParking(duration);
    }

    private Ticket createTicketForSlot(Vehicle vehicle, ParkingSlot parkingSlot) {
        return Ticket.createTicket(vehicle,parkingSlot);
    }


    public ParkingSlot getParkingSlotForVehicleAndPark(Vehicle vehicle){
        ParkingSlot parkingSlot = null;
        for(ParkingFloor parkingFloor : parkingFloors){
            parkingSlot = parkingFloor.FindRelevantSpot(vehicle);
            if(parkingSlot!=null) break;
        }
        return parkingSlot;
    }


}
