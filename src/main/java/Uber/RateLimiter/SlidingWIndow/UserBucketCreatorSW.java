package Uber.RateLimiter.SlidingWIndow;

import java.util.HashMap;
import java.util.Map;

public class UserBucketCreatorSW {

    Map<Integer, SlidingWindow> bucket;
    public UserBucketCreatorSW(int userId){
        bucket = new HashMap<>();
        bucket.put(userId,new SlidingWindow(1,10));
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
