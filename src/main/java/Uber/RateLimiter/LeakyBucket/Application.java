package Uber.RateLimiter.LeakyBucket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
    public static void main(String args[]){
        System.out.println("Hello from API Rate Limiter");

        UserBucketCreator userBucketCreator = new UserBucketCreator(123);
        ExecutorService executorService = Executors.newFixedThreadPool(12);
        for(int i=0;i<12;i++){
            executorService.execute(()-> userBucketCreator.accessApplication(123));
        }
        executorService.shutdown();
    }
}
