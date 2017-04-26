package com.wjusite.boot.redis;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @ClassName: RedisAutoConfiguration
 * @Description: Redis自动配置类
 * @author Kola 6089555
 * @date 2017年4月26日 下午4:48:31
 *
 */
@Configuration
@ConditionalOnClass(value = { JedisPoolConfig.class, JedisShardInfo.class, ShardedJedisPool.class })
public class RedisAutoConfiguration {
    
    @Bean
    @ConfigurationProperties(prefix = "redis.pool")
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }
    
    @Bean
    @ConfigurationProperties(prefix = "redis.host")
    public JedisShardInfo jedisShardInfo(@Value("${redis.host.ip:#{null}}") String hostIp, @Value("${redis.host.port:#{null}}") Integer hostPort) {
        return new JedisShardInfo(hostIp, hostPort);
    }
    
    @Bean
    @ConditionalOnBean(value = { JedisPoolConfig.class, JedisShardInfo.class })
    public ShardedJedisPool shardedJedisPool(JedisPoolConfig jedisPoolConfig, JedisShardInfo jedisShardInfo) {
        return new ShardedJedisPool(jedisPoolConfig, Arrays.asList(new JedisShardInfo[] { jedisShardInfo }));
    }
    
}
