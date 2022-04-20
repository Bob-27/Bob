package com.videoSite.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration//配置类，使得配置信息能够读取到
@EnableTransactionManagement
@MapperScan("com.videoSite.mapper")
public class MybatisXConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor(){//拦截器，专门用来做分页
          PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
          return paginationInterceptor;
      }
}
