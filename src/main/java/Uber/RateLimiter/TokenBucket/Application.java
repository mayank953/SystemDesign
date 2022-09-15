package Uber.RateLimiter.TokenBucket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
    public static void main(String args[]){
        UserBucketCreatorTB userBucketCreatorTB = new UserBucketCreatorTB(123);
        ExecutorService executorService = Executors.newFixedThreadPool(12);

        for(int i=0;i<12;i++){
            executorService.execute(()-> userBucketCreatorTB.accessApplication(123));
        }
        executorService.shutdown();
    }
}
