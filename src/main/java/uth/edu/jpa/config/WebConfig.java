package uth.edu.jpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ánh xạ /uploads/** tới thư mục uploads tại gốc project
        registry.addResourceHandler("/Uploads/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/Uploads/");
    }


}