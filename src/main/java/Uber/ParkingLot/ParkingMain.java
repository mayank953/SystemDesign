package Uber.ParkingLot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingMain {
    public static void main(String args[]) throws InterruptedException {
        String name = "My Parking";
        Map<ParkingSlotType, Map<String,ParkingSlot>> allSlots = new HashMap<>();

        Map<String,ParkingSlot> compactSlot = new HashMap<>();
        compactSlot.put("C1",new ParkingSlot("C1",ParkingSlotType.COMPACT));
        compactSlot.put("C2",new ParkingSlot("C2",ParkingSlotType.COMPACT));
        compactSlot.put("C3",new ParkingSlot("C3",ParkingSlotType.COMPACT));
        allSlots.put(ParkingSlotType.COMPACT,compactSlot);
        Map<String,ParkingSlot> largeSlot = new HashMap<>();
        largeSlot.put("L1",new ParkingSlot("L1",ParkingSlotType.TWO_WHEELER));
        largeSlot.put("L2",new ParkingSlot("L2",ParkingSlotType.TWO_WHEELER));
        largeSlot.put("L3",new ParkingSlot("L3",ParkingSlotType.TWO_WHEELER));
        allSlots.put(ParkingSlotType.TWO_WHEELER,largeSlot);
        ParkingFloor parkingFloor = new ParkingFloor("1",allSlots);

        List<ParkingFloor> parkingFloorList = new ArrayList<>();
        parkingFloorList.add(parkingFloor);

        ParkingLot parkingLot  = ParkingLot.getInstance(name,parkingFloorList);
        Vehicle vehicle = new Vehicle("1234567",ParkingSlotType.COMPACT);

        Ticket ticket = parkingLot.assignTicket(vehicle);
        System.out.println(" ticket number >> "+ticket.getName());
        //persist the ticket to db here
        Thread.sleep(100);
        double price = parkingLot.scanAndPay(ticket);
        System.out.println("price is >>" + price);

    }
}
