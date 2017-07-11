package base.redis;

import java.util.UUID;

/**
 * Created by shsun on 7/11/17.
 */
public class ABC implements IXRedisLock {
    @Override
    public UUID getLockUUID() {
        return null;
    }

    @Override
    public String getLockKeyPath() {
        return null;
    }

    @Override
    public boolean acquire() throws InterruptedException {
        return false;
    }

    @Override
    public boolean renew() throws InterruptedException {
        return false;
    }

    @Override
    public void release() {

    }

    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public synchronized long getLockExpiryTimeInMillis() {
        return 0;
    }
}
