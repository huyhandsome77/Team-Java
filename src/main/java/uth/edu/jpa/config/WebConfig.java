package uth.edu.jpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ánh xạ /uploads/** tới thư mục uploads tại gốc project
        registry.addResourceHandler("/Uploads/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/Uploads/");
    }
}
