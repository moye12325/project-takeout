package com.moye.config;

import com.moye.properties.CloudStorageConfig;
import com.moye.utils.QiNiuOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OssConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public QiNiuOssUtil qiNiuOssUtil(CloudStorageConfig cloudStorageConfig) {
        log.info("开始上传七牛云文件上传工具类对象：{}", cloudStorageConfig);
        return new QiNiuOssUtil(cloudStorageConfig.getQiniuAccessKey(),
                cloudStorageConfig.getQiniuSecretKey(),
                cloudStorageConfig.getQiniuBucketName(),
                cloudStorageConfig.getQiniuDomain());

    }
}
