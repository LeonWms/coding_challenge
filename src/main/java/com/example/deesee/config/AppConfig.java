package com.example.deesee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${encryption.key}")
    private int encryptionKey;

    @Bean
    public int encryptionKey() {
        return encryptionKey;
    }
}
