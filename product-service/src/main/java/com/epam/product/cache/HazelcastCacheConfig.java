package com.epam.product.cache;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastCacheConfig {

    @Bean
    public Config hazelcastConfig(){
        return new Config().setInstanceName("hazelcast-instance")
                .addMapConfig(new MapConfig().setName("product")
                        .setTimeToLiveSeconds(600));
    }
}