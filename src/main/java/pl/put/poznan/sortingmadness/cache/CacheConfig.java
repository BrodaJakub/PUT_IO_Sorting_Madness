package pl.put.poznan.sortingmadness.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public CacheKeyBuilder cacheKeyBuilder(ObjectMapper objectMapper) {
        return new CacheKeyBuilder(objectMapper);
    }
}
