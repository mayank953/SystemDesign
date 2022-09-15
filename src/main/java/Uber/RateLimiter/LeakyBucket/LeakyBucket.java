package Uber.RateLimiter.LeakyBucket;

import Uber.RateLimiter.RateLimiter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LeakyBucket implements RateLimiter {
    BlockingQueue<Integer> queue;

    LeakyBucket(int capacity){
        this.queue = new LinkedBlockingQueue<>(capacity);
    }




    @Override
    public boolean grantAccess() {
        if(queue.remainingCapacity()>0){
            queue.add(1); // assuming 1 request is coming per request
            return true;
        }
        return false;
    }
}
