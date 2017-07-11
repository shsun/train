package base.redis;

import java.util.UUID;

/**
 * Created by shsun on 7/11/17.
 */
interface IXRedisLock {

    /**
     * @return lock uuid
     */
    UUID getLockUUID();

    /**
     * @return lock key path
     */
    String getLockKeyPath();

    /**
     * Acquire lock.
     *
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException in case of thread interruption
     */
    boolean acquire() throws InterruptedException;

    /**
     * Renew lock.
     *
     * @return true if lock is acquired, false otherwise
     * @throws InterruptedException in case of thread interruption
     */
    boolean renew() throws InterruptedException;

    /**
     * Acquired lock release.
     */
    void release();

    /**
     * Check if owns the lock
     *
     * @return true if lock owned
     */
    boolean isLocked();

    /**
     * Returns the expiry time of this lock
     *
     * @return the expiry time in millis (or null if not locked)
     */
    long getLockExpiryTimeInMillis();
}
