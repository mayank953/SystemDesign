package Uber.RateLimiter.LeakyBucket;

public interface RateLimiter {
    boolean grantAccess();
}
