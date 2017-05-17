package base.redis;

import java.util.Hashtable;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;


public class RedisConfig extends JedisPoolConfig {

	
	private String host = "";//Protocol.DEFAULT_HOST;
	private int port = Protocol.DEFAULT_PORT;
	private int connectionTimeout = Protocol.DEFAULT_TIMEOUT;
	private int soTimeout = 500;//Protocol.DEFAULT_TIMEOUT;
	private String password;
	private int database = 1;//Protocol.DEFAULT_DATABASE;
	private String clientName;

	private final Hashtable<String, Integer> settings = new Hashtable<String, Integer>();

	public String getClientName() {
		return clientName;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public int getDatabase() {
		return database;
	}

	public String getHost() {
		return host;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

	public Hashtable<String, Integer> getSettings() {
		return settings;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setClientName(String clientName) {
		if ("".equals(clientName)) {
			clientName = null;
		}
		this.clientName = clientName;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public void setHost(String host) {
		if (host == null || "".equals(host)) {
			host = "";//Protocol.DEFAULT_HOST;
		}
		this.host = host;
	}

	public void setPassword(String password) {
		if ("".equals(password)) {
			password = null;
		}
		this.password = password;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

}
