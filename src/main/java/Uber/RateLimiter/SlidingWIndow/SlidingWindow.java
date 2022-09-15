package Uber.RateLimiter.SlidingWIndow;

import Uber.RateLimiter.RateLimiter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SlidingWindow implements RateLimiter {

    Queue<Long> slidingWindow;
    int timeWindowInSec;
    int bucketCapacity;

    public SlidingWindow(int timeWindowInSec,int bucketCapacity){
        this.bucketCapacity = bucketCapacity;
        this.timeWindowInSec = timeWindowInSec;
        slidingWindow = new ConcurrentLinkedQueue<>();
    }
    @Override
    public boolean grantAccess() {
        long currentTime = System.currentTimeMillis();
        checkAndUpdateQueue(currentTime);
        if(slidingWindow.size() < bucketCapacity){
            slidingWindow.offer( currentTime);

            return true;
        }
        return false;
    }

    private void checkAndUpdateQueue(long currentTime) {
        if(slidingWindow.isEmpty()) return;

        while((slidingWindow.size() > 0) && ((currentTime - (slidingWindow.peek() / 1000)) > timeWindowInSec)) {
            slidingWindow.poll();
        }
    }
}
