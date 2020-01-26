package com.oauth.authServer.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${redis.host.ip:localhost}")
    private String host;

    @Value("${redis.host.port:6379}")
    private int port;

    @Value("${redis.host.password:}")
    private String password;

    @Bean
    LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host,port);
        config.setPassword(password);
        LettuceConnectionFactory factory =  new LettuceConnectionFactory(config);
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory());
        return template;
    }

    @Bean
    public RedissonClient getRedissonClient(){
        StringBuilder builder = new StringBuilder();
        builder.append("redis://");
        builder.append(host);
        builder.append(":");
        builder.append(port);
        Config config = new Config();
        config.useSingleServer().setAddress(builder.toString());
        config.useSingleServer().setPassword(password);
        return Redisson.create(config);
    }
}
