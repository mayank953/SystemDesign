package CarRentalSystem.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    int Id;
    int vehicleNumber;
    String Name;

    VechicleType vechicleType;
    String Company;
    int pricePerDay;
    String color;
    Status status;
    int average,cc,kilometersDriven;



}
