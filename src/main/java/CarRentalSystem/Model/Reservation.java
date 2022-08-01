package CarRentalSystem.Model;

import java.sql.Timestamp;
import java.util.Date;

public class Reservation {

    int reservationId;
    User user;
    Vehicle vehicle;
    Date startDate;
    Date endDate;
    Timestamp startTime;
    Timestamp endTime;
    int billAmount;
    Location pickUpLocation;
    Location dropLocation;
    ReservationStatus reservationStatus;


    public int createReservation(User user,Vehicle vehicle){
        this.reservationId = 123;
        this.user = user;
        this.vehicle = vehicle;

        return reservationId;
    }
}
