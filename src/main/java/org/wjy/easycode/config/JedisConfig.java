package org.wjy.easycode.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

/**
 * add jedis client to solve https://github.com/lettuce-io/lettuce-core/issues/817
 * 
 * @author weijiayu
 * @date 2021/10/9 11:44
 */
@Configuration
public class JedisConfig {

    @Value("${spring.redis.host}")
    private String redisHostName;
    @Value("${spring.redis.database}")
    private int redisDatabaseIndex;
    @Value("${spring.redis.port}")
    private int redisPort;
    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean(name = "jedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(JedisPoolConfig jedisPoolConfig) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 配置序列化和反序列化
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 配置redis连接
        RedisStandaloneConfiguration jedisConfig = new RedisStandaloneConfiguration();
        jedisConfig.setHostName(redisHostName);
        jedisConfig.setDatabase(redisDatabaseIndex);
        jedisConfig.setPort(redisPort);
        jedisConfig.setPassword(redisPassword);
        // 配置连接池
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
            (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
        jpcb.poolConfig(jedisPoolConfig);
        JedisClientConfiguration jc = jpcb.build();
        // 配置连接工厂
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisConfig, jc);
        // 配置模板类
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        // jedis连接池
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大连接数
        jedisPoolConfig.setMaxTotal(100);
        // 最大空闲连接数
        jedisPoolConfig.setMaxIdle(20);
        // 最大等待时间。当池内没有可用连接时
        jedisPoolConfig.setMaxWaitMillis(10000);
        return jedisPoolConfig;
    }

}
