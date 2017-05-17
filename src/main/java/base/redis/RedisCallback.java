package base.redis;

import redis.clients.jedis.Jedis;

public interface RedisCallback {

	Object doWithRedis(Jedis jedis);
}
