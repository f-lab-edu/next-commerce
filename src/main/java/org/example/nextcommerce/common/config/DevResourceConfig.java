package org.example.nextcommerce.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Profile("dev")
@Configuration
public class DevResourceConfig implements WebMvcConfigurer {

    @Value("${nextcommerce.image.download.location}")
    private String devFilePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/download/**")
                .addResourceLocations("file:"+devFilePath);
            //    .setCacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES));
    }
}
