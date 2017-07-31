package com.tomhellier.ml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tomhellier.ml.storage.StorageProperties;
import com.tomhellier.ml.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tomhellier.ml")
@EnableConfigurationProperties(StorageProperties.class)
public class TensorFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(TensorFlowApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}

