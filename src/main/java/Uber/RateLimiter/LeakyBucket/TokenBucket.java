package Uber.RateLimiter.LeakyBucket;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TokenBucket implements RateLimiter{

    private int bucketCapacity;
    private int refreshRate;
    private AtomicInteger currentCapacity;
    private AtomicLong lastUpdatedTime;

    public TokenBucket(int bucketCapacity,int refreshRate){
        this.bucketCapacity=  bucketCapacity;
        this.refreshRate = refreshRate;
        currentCapacity.getAndSet(bucketCapacity);
        lastUpdatedTime.getAndSet(System.currentTimeMillis());
    }


    @Override
    public boolean grantAccess() {
        refreshBucket();

        if(currentCapacity.get()>0){
            currentCapacity.decrementAndGet();
            return true;
        }
        return false;
    }

    private void refreshBucket() {
        long currentTime = System.currentTimeMillis();
        int additionalToken = (int) ((currentTime-lastUpdatedTime.get())/1000 *refreshRate);
        int currCapcity = Math.min(currentCapacity.get() + additionalToken,bucketCapacity);
        currentCapacity.getAndSet(currCapcity);
        lastUpdatedTime.getAndSet(currentTime);
    }
}
