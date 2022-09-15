package elevatorDesign.Model;


// dumb object, not hold logic
public class ElevatorCar {
    int id;
    ElevatorDisplay display;
    Direction direction;
    ElevatorState elevatorState;
    InternalButton internalButton;
    int currentFloor;

    public ElevatorCar(){
        display = new ElevatorDisplay();
        direction = Direction.UP;
        currentFloor =0;
        elevatorState = ElevatorState.IDLE;
        internalButton = new InternalButton();

    }

    public void showDisplay() {
        display.showDisplay();
    }

    public void pressButton(int destination) {

        internalButton.ressButton(destination, this);
    }

    public void setDisplay() {
        this.display.setDisplay(currentFloor, direction);
    }


    public boolean moveElevator(Direction dir,int destinationFloor){
        int startFloor = currentFloor;

        if(dir == Direction.UP) {
            for(int i = startFloor; i<=destinationFloor; i++) {
                this.currentFloor = startFloor;
                setDisplay();
                showDisplay();
                if(i == destinationFloor) {
                    return true;
                }
            }
        }

        if(dir == Direction.DOWN) {
            for(int i = startFloor; i>=destinationFloor; i--) {

                this.currentFloor = startFloor;
                setDisplay();
                showDisplay();
                if(i == destinationFloor) {
                    return true;
                }
            }
        }
        return false;



    }
}
