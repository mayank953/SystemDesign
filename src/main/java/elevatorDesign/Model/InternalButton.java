package elevatorDesign.Model;

public class InternalButton {
    InternalDispatcher dispatcher= new InternalDispatcher();
    int []buttonAvailable = {1,2,3,4,5,6,7,8,9,10};
    int buttonSelected;


    void pressButton(int destination, ElevatorCar elevatorCar){

        dispatcher.submitInternalRequest(destination, elevatorCar);
    }


    public void ressButton(int destination, ElevatorCar elevatorCar) {
    }
}
