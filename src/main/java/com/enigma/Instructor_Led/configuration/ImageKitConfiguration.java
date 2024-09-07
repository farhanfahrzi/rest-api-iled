package com.enigma.Instructor_Led.configuration;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class ImageKitConfiguration {

    @PostConstruct
    public void init() {
        try {
            Configuration config = new Configuration(
                    "public_2zjJkc2dXhvDPmN5EeDu6YBf2io=",
                    "private_OTltvfdlwZkSQRO8jESQpM6SuZs=",
                    "https://imagekit.io/dashboard/url-endpoints/lksjdf7sd");
            ImageKit.getInstance().setConfig(config);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing ImageKit configuration: " + e.getMessage(), e);
        }
    }
}