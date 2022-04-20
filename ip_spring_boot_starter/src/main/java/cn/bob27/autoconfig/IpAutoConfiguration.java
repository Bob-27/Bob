package cn.bob27.autoconfig;

import cn.bob27.properties.IpProperties;
import cn.bob27.service.IpCountService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Import(IpProperties.class)
public class IpAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public IpCountService ipCountService() {
        return new IpCountService();
    }
}
