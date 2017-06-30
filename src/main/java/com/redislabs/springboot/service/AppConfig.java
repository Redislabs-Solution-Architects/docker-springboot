package com.redislabs.springboot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Set;

@Configuration
@ComponentScan("com.redislabs.springboot.service")
public class AppConfig {

    private @Value("${redis.sentinel.master}") String sentinelMaster;
    private @Value("${redis.sentinel.nodes}") String sentinelNodes;


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        System.out.println(sentinelMaster);
        System.out.println(sentinelNodes);


        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration();

        Set<String> hostAndPorts = StringUtils.commaDelimitedListToSet(sentinelNodes);
        Iterator itr = hostAndPorts.iterator();
        while(itr.hasNext()) {
            String hostAndPort = (String)itr.next();
            String[] args = StringUtils.split(hostAndPort, ":");
            System.out.println(hostAndPort);
            sentinelConfig.sentinel(args[0],Integer.valueOf(args[1]).intValue());
        }

        sentinelConfig.master(sentinelMaster);

        JedisConnectionFactory factory = new JedisConnectionFactory(sentinelConfig);
        factory.setUsePool(true);
        return factory;

    }

    @Bean
    RedisTemplate< String, Object > redisStringTemplate() {
        final RedisTemplate< String, Object > template =  new RedisTemplate< String, Object >();
        template.setConnectionFactory( jedisConnectionFactory() );
        template.setKeySerializer( new StringRedisSerializer() );
        template.setHashValueSerializer( new GenericToStringSerializer< Object >( Object.class ) );
        template.setValueSerializer( new GenericToStringSerializer< Object >( Object.class ) );
        return template;
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {

        RedisTemplate<byte[], byte[]> template = new RedisTemplate<byte[], byte[]>();
        template.setConnectionFactory( jedisConnectionFactory() );
        return template;
    }
}
