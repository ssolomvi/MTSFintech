package org.example;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConditionalOnClass(...)
@EnableConfigurationProperties(Properties.class)
public class Configurations {
}
