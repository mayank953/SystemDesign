package Uber.RateLimiter.LeakyBucket;

import java.util.HashMap;
import java.util.Map;

public class UserBucketCreator {

    Map<Integer,LeakyBucket> bucket;
    public  UserBucketCreator(int userId){
        bucket = new HashMap<>();
        bucket.put(userId,new LeakyBucket(10));
    }

    void accessApplication(int userId)  {
        if(bucket.get(userId).grantAccess()){
            System.out.println(Thread.currentThread().getName()+ "-> Able to access the app");
        }
        else{
            System.out.println(Thread.currentThread().getName() + "->Lot of request, try after some time");
        }
    }
}
