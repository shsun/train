package base.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.cache.CacheException;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**
 * Converter from the Config to a proper {@link RedisConfig}.
 * 
 * @author Eduardo Macarron
 */
final class RedisConfigurationBuilder {

	/**
	 * This class instance.
	 */
	private static final RedisConfigurationBuilder INSTANCE = new RedisConfigurationBuilder();

	private static final String SYSTEM_PROPERTY_REDIS_PROPERTIES_FILENAME = "redis.properties.filename";

	private static final String REDIS_RESOURCE = "redis.properties";

	/**
	 * Return this class instance.
	 * 
	 * @return this class instance.
	 */
	public static RedisConfigurationBuilder getInstance() {
		return INSTANCE;
	}

	private final String redisPropertiesFilename;

	/**
	 * Hidden constructor, this class can't be instantiated.
	 */
	private RedisConfigurationBuilder() {
		redisPropertiesFilename = System.getProperty(SYSTEM_PROPERTY_REDIS_PROPERTIES_FILENAME, REDIS_RESOURCE);
	}

	private boolean isInteger(String s) {
		return isInteger(s, 10);
	}

	private boolean isInteger(String s, int radix) {
		if (s.isEmpty()) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1) {
					return false;
				} else {
					continue;
				}
			}
			if (Character.digit(s.charAt(i), radix) < 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Parses the Config and builds a new {@link RedisConfig}.
	 * 
	 * @return the converted {@link RedisConfig}.
	 */
	public RedisConfig parseConfiguration() {
		return parseConfiguration(getClass().getClassLoader());
	}

	/**
	 * Parses the Config and builds a new {@link RedisConfig}.
	 * 
	 * @param the
	 *            {@link ClassLoader} used to load the
	 *            {@code memcached.properties} file in classpath.
	 * @return the converted {@link RedisConfig}.
	 */
	public RedisConfig parseConfiguration(ClassLoader classLoader) {
		final Properties config = new Properties();

		final InputStream input = classLoader.getResourceAsStream(redisPropertiesFilename);
		if (input != null) {
			try {
				config.load(input);
			} catch (final IOException e) {
				throw new RuntimeException("An error occurred while reading classpath property '"
						+ redisPropertiesFilename + "', see nested exceptions", e);
			} finally {
				try {
					input.close();
				} catch (final IOException e) {
					// close quietly
				}
			}
		}

		final RedisConfig jedisConfig = new RedisConfig();
		jedisConfig.setHost("localhost");
		setConfigProperties(config, jedisConfig);
		return jedisConfig;
	}

	private void setConfigProperties(Properties properties, RedisConfig jedisConfig) {
		if (properties != null) {
			final MetaObject metaCache = SystemMetaObject.forObject(jedisConfig);
			for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
				final String name = (String) entry.getKey();
				final String value = (String) entry.getValue();
				if (metaCache.hasSetter(name)) {
					final Class<?> type = metaCache.getSetterType(name);
					if (String.class == type) {
						metaCache.setValue(name, value);
					} else if (int.class == type || Integer.class == type) {
						metaCache.setValue(name, Integer.valueOf(value));
					} else if (long.class == type || Long.class == type) {
						metaCache.setValue(name, Long.valueOf(value));
					} else if (short.class == type || Short.class == type) {
						metaCache.setValue(name, Short.valueOf(value));
					} else if (byte.class == type || Byte.class == type) {
						metaCache.setValue(name, Byte.valueOf(value));
					} else if (float.class == type || Float.class == type) {
						metaCache.setValue(name, Float.valueOf(value));
					} else if (boolean.class == type || Boolean.class == type) {
						metaCache.setValue(name, Boolean.valueOf(value));
					} else if (double.class == type || Double.class == type) {
						metaCache.setValue(name, Double.valueOf(value));
					} else {
						throw new CacheException("Unsupported property type: '" + name + "' of type " + type);
					}
				} else if (isInteger(value)) {
					jedisConfig.getSettings().put(name, Integer.parseInt(value));
				}
			}
		}
	}
}