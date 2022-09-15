package Uber.ParkingLot;

import java.util.Map;

public class ParkingFloor {
    int id;
    String name;
    Map<ParkingSlotType, Map<String,ParkingSlot>> parkingSlots;

    ParkingFloor(String name,Map<ParkingSlotType, Map<String,ParkingSlot>> parkingSlots){
        this.name=name;
        this.parkingSlots=parkingSlots;
    }

    public ParkingSlot FindRelevantSpot(Vehicle vehicle){
        ParkingSlotType parkingSlotType = vehicle.getParkingSlotType();
        Map<String,ParkingSlot> relevantParkingSlot = parkingSlots.get(parkingSlotType);
        ParkingSlot slot =null ;
        for(Map.Entry<String,ParkingSlot> m : relevantParkingSlot.entrySet()){
            if(m.getValue().isAvailable){
                slot=m.getValue();
                slot.addVehicle(vehicle);
                break;
            }
        }
        return slot;
    }

}
