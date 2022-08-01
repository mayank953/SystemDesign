package CarRentalSystem.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Location {
    String address;
    String city;
    int pinCode;
    String state;
    String country;

}
