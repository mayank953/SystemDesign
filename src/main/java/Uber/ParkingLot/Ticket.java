package Uber.ParkingLot;

public class Ticket {
    String name;
    int id;
    long startTime;
    long endTime;
    Vehicle vehicle;
    ParkingSlot parkingSlot;

    Ticket(String name,int id,long startTime,Vehicle vehicle,ParkingSlot parkingSlot){
        this.id=id;
        this.name = name;
        this.startTime=startTime;
        this.vehicle=vehicle;
        this.parkingSlot =parkingSlot;
    }

    public long getStartTime() {
        return startTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Ticket createTicket(Vehicle vehicle, ParkingSlot parkingSlot){
        String name = vehicle.getNumber();
        int id = Integer.parseInt(vehicle.getNumber());
        long startTime = System.currentTimeMillis();
        return new Ticket(name,id,startTime,vehicle,parkingSlot);
    }
}
