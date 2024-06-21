package com.example.greenstoreproject.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean(name = "customCloudinaryConfig")
    public Cloudinary cloudinaryConfig() {
        Cloudinary cloudinary = null;
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dmmk9racr");
        config.put("api_key", "112423164676247");
        config.put("api_secret", "shS7Rj3nEjTIKF2wEUEUw-s8x1k");
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }


}
