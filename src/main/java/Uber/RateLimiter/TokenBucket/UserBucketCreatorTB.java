package Uber.RateLimiter.TokenBucket;

import java.util.HashMap;
import java.util.Map;

public class UserBucketCreatorTB {
    Map<Integer,TokenBucket> bucket;
    public UserBucketCreatorTB(int userId){
        bucket = new HashMap<>();
        bucket.put(userId,new TokenBucket(10,10));
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
