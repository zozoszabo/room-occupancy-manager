package com.interview.roomoccupancymanager.configuration;

import java.util.Collection;
import java.util.List;

import com.interview.roomoccupancymanager.configuration.value.ApplicationValue;
import com.interview.roomoccupancymanager.service.util.MoneyConverters;
import lombok.NonNull;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConfigurationPropertiesScan(basePackages = "com.interview.roomoccupancymanager.configuration.value")
public class ApplicationConfiguration {

    @Bean
    public Collection<Money> guestsMock(@NonNull final ApplicationValue applicationValue) {
        return MoneyConverters.convert(
                applicationValue.getCurrencyUnit(),
                List.of(23.0D, 45.0D, 155.0D, 374.0D, 22.0D, 99.99D, 100.0D, 101.0D, 115.0D, 209.0D)
        );
    }

    @Bean
    public Docket getDocker(@Value("${room-occupancy-manager.name}") final String name,
                            @Value("${room-occupancy-manager.version}") final String version) {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.interview.roomoccupancymanager.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder().version(version).title(name).build());
    }

}
