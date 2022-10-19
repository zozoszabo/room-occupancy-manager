package com.interview.roomoccupancymanager.configuration;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackages = "com.interview.roomoccupancymanager.configuration.value")
public class ApplicationConfiguration {

}
