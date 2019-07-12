package com.github.flybooter.autoconfigure.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Configuration
public class RedisClusterConfig {

	@Autowired
	private RedisClusterProperties clusterProperties;

	@Bean
	public RedisClusterConfiguration getClusterConfig() {
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterProperties.getNodes());
		redisClusterConfiguration.setMaxRedirects(clusterProperties.getMaxRedirects().intValue());
		return redisClusterConfiguration;
	}

	@Bean
	public JedisConnectionFactory getConnectionFactory(RedisClusterConfiguration cluster) {
		return new JedisConnectionFactory(cluster);
	}

	@Bean
	public RedisTemplate getRedisTemplate(JedisConnectionFactory factory) {
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(factory);
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(redisSerializer);
		return redisTemplate;
	}

	@Bean
	public StringRedisTemplate getStringRedisTemplate(RedisConnectionFactory factory) {
		StringRedisTemplate stringTemplate = new StringRedisTemplate();
		stringTemplate.setConnectionFactory(factory);
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();// Long���Ͳ����Ի�����쳣��Ϣ;
		stringTemplate.setKeySerializer(redisSerializer);
		stringTemplate.setHashKeySerializer(redisSerializer);
		stringTemplate.setValueSerializer(redisSerializer);
		return stringTemplate;
	}

	/**
	 * 这里返回的JedisCluster是单例的，并且可以直接注入到其他类中去使用
	 * @return
	 */
	@Bean
	public JedisCluster jedisCluster() {
		List<String> nodes = clusterProperties.getNodes();
		Set<HostAndPort> nodeSet = new HashSet<>();
		for(String node :nodes) {
			String[] split = node.split(":");
			nodeSet.add(new HostAndPort(split[0],Integer.valueOf(split[1])));
		}
		return new JedisCluster(nodeSet);
	}

}
