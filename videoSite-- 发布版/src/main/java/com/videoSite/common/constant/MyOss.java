package com.videoSite.common.constant;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Data
@Component
@ConfigurationProperties(prefix = "my-oss")
public class MyOss {
     String ENDPOINT;
     String ACCESS_KEY_Id;
     String ACCESS_KEY_SECRET;
     String BUCKET_NAME;
     public OSS build(){
        return  new OSSClientBuilder().build(ENDPOINT,ACCESS_KEY_Id,ACCESS_KEY_SECRET);
     }
}
