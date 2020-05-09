package cn.notemi.demo.config;

import cn.notemi.demo.enums.CacheKeyEnum;
import cn.notemi.demo.model.bo.LoginToken;
import cn.notemi.demo.service.LoginTokenService;
import cn.notemi.demo.service.impl.LoginTokenCacheServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
@ConditionalOnClass(value = { LoginTokenCacheServiceImpl.class, RedisTemplate.class })
public class TokenBeanConfig {

    @Bean
    @ConditionalOnMissingBean(name = "loginTokenService")
    public LoginTokenService loginTokenService(RedisTemplate<String, LoginToken> loginTokenRedisTemplate) {
        return new LoginTokenCacheServiceImpl(loginTokenRedisTemplate, CacheKeyEnum.VALUE_LOGIN_TOKENS.code());
    }
    
    @Bean
    public RedisTemplate<String, LoginToken> loginTokenRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, LoginToken> tokenRedisTemplate = new RedisTemplate<>();
        tokenRedisTemplate.setKeySerializer(new GenericToStringSerializer<>(String.class));
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Jackson2JsonRedisSerializer<LoginToken> serializer = new Jackson2JsonRedisSerializer<>(LoginToken.class);
        serializer.setObjectMapper(mapper);
        tokenRedisTemplate.setValueSerializer(serializer);
        tokenRedisTemplate.setConnectionFactory(connectionFactory);
        return tokenRedisTemplate;
    }
}