package Uber.ParkingLot;

public class ParkingSlot {
    String name;
    Boolean isAvailable=Boolean.TRUE;

    Vehicle vehicle;
    ParkingSlotType parkingSlotType;

    public ParkingSlot(String name,ParkingSlotType parkingSlotType){
        this.parkingSlotType= parkingSlotType;
        this.name=name;
    }
    public void addVehicle(Vehicle vehicle){
        this.vehicle=vehicle;
        isAvailable=false;
    }

    public void removeVehicle(Vehicle vehicle){
        this.vehicle=null;
        isAvailable=true;
    }
}
