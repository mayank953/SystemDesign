package CarRentalSystem.Model;

import java.util.List;

public class VehicleInventoryManagement {
    List<Vehicle> vehicleList;

    VehicleInventoryManagement(List<Vehicle> vehicleList){
        this.vehicleList = vehicleList;
    }


    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }
}
