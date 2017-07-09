package base.redis;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by shsun on 7/9/17.
 */
public class ShardedJedisContainer {

    /** The ReadWriteLock. */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private String id;

    private ShardedJedisPool shardedJedisPool;

    /**
     *
     * @param pool
     */
    public ShardedJedisContainer(final ShardedJedisPool pool) {
        this.shardedJedisPool = pool;
    }

    public void putObject(Object key, Object value) {
        System.out.println("ShardedJedisContainer put key=" + key + ", value=" + value);
        ShardedJedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.set(SerializeUtil.serialize(key.hashCode()), SerializeUtil.serialize(value));
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (jedis != null) {
                shardedJedisPool.returnBrokenResource(jedis);
            }
        } finally {
            if (borrowOrOprSuccess) {
                shardedJedisPool.returnResource(jedis);
            }
        }
    }

    public Object getObject(Object key) {
        ShardedJedis jedis = null;
        Object value = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = shardedJedisPool.getResource();
            value = SerializeUtil.unserialize(jedis.get(SerializeUtil.serialize(key.hashCode())));
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (jedis != null) {
                shardedJedisPool.returnBrokenResource(jedis);
            }
        } finally {
            if (borrowOrOprSuccess) {
                shardedJedisPool.returnResource(jedis);
            }
        }
        return value;
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @param forcePut true indicate put the defaultValue into redis, otherwise not.
     * @return
     */
    public Object getObject(Object key, Object defaultValue, Boolean forcePut) {
        Object value = getObject(key);
        if (value == null) {
            value = defaultValue;
            if (forcePut) {
                putObject(key, value);
            }
        }
        return value;
    }

    public Object removeObject(Object key) {
        System.out.println("ShardedJedisContainer remove key " + key + ", 到了设定时间");
        ShardedJedis jedis = null;
        Object value = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = shardedJedisPool.getResource();
            value = jedis.expire(SerializeUtil.serialize(key.hashCode()), 0);
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (jedis != null) {
                shardedJedisPool.returnBrokenResource(jedis);
            }
        } finally {
            if (borrowOrOprSuccess) {
                shardedJedisPool.returnResource(jedis);
            }
        }
        return value;
    }

    public void clear() {
        System.out.println("RedisCache clear all data");
        ShardedJedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = shardedJedisPool.getResource();
            // jedis.flushDB();
            // jedis.flushAll();
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                shardedJedisPool.returnBrokenResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
                shardedJedisPool.returnResource(jedis);
        }
    }

    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

}
