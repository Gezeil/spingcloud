package com.yanjun.xiang.common.util;

public interface DistributedLock {
    long TIMEOUT_MILLIS = 5000L;
    int RETRY_TIMES = 10;
    long SLEEP_MILLIS = 100L;

    default boolean lock(String key) {
        return this.lock(key, 5000L, 10, 100L);
    }

    default boolean lock(String key, int retryTimes) {
        return this.lock(key, 5000L, retryTimes, 100L);
    }

    default boolean lock(String key, int retryTimes, long sleepMillis) {
        return this.lock(key, 5000L, retryTimes, sleepMillis);
    }

    default boolean lock(String key, long expire) {
        return this.lock(key, expire, 10, 100L);
    }

    default boolean lock(String key, long expire, int retryTimes) {
        return this.lock(key, expire, retryTimes, 100L);
    }

    boolean lock(String var1, long var2, int var4, long var5);

    default boolean tryLock(String key) {
        return this.lock(key, 5000L, 0, 0L);
    }

    default boolean tryLock(String key, long timeoutMillis) {
        return this.lock(key, timeoutMillis, 0, 0L);
    }

    boolean releaseLock(String var1);
}
