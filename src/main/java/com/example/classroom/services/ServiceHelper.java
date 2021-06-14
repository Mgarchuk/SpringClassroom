package com.example.classroom.services;

import com.example.classroom.codecs.CustomJsonJacksonCodec;
import com.example.classroom.configuration.RedisConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceHelper {
    protected RedisConfig redisConfig;
    protected RedissonClient redisson;
    private static final Logger log = LoggerFactory.getLogger(ServiceHelper.class);

    @Autowired
    public void setRedisConfig(RedisConfig redisConfig) {
        try {
            this.redisConfig = redisConfig;

            Config redissonConfig = new Config();
            redissonConfig.setCodec(new CustomJsonJacksonCodec()).useSingleServer().setAddress(redisConfig.getUrl()).setPassword(redisConfig.getPassword());
            redisson = Redisson.create(redissonConfig);
        } catch (Exception ex) {
            log.error("Can't connect to redis, reason is: " + ex);
        }
    }
}
