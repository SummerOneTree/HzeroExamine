package org.hzero.todo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

/**
 * @Date 2020/8/7 15:38
 * @Author Summer_OneTree
 */
@Configuration
public class RedisConfig {

    /**
     * 自己定义一个RedisTemplate模板
     *
     * @param redisConnectionFactory
     * @return
     * @throws UnknownHostException
     */
    @Bean("myRedisTemplate")
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        //为了开发方便，一般使用<String, Object>
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        //Json序列化配置
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);
        // String的序列化
        StringRedisSerializer redisSerializer = new StringRedisSerializer();

        // key采用String的序列化方式
        template.setKeySerializer(redisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(redisSerializer);
        // value的序列化采用jackson
        template.setValueSerializer(serializer);
        // hash的value序列化采用jackson
        template.setHashValueSerializer(serializer);

        template.setConnectionFactory(redisConnectionFactory);

        template.afterPropertiesSet();
        return template;
    }
}
