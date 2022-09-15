package Uber.ParkingLot;

public enum ParkingSlotType {
    TWO_WHEELER{
        @Override
        public double getPriceForParking(long duration) {
            return duration * 0.05;
        }
    },
    COMPACT{
        @Override
        public double getPriceForParking(long duration) {
            return duration * 0.5;
        }
    };


    public abstract double getPriceForParking(long duration);
}
