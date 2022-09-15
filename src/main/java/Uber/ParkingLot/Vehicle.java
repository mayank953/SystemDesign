package Uber.ParkingLot;

public class Vehicle {
    String number;
    ParkingSlotType parkingSlotType;

    public Vehicle(String number,ParkingSlotType parkingSlotType){
        this.number = number;
        this.parkingSlotType = parkingSlotType;
    }

    public String getNumber() {
        return number;
    }

    public ParkingSlotType getParkingSlotType() {
        return parkingSlotType;
    }
}
