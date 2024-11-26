package com.whoz_in.domain_log_jpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "com.whoz_in.common_domain_jpa")
@ComponentScan(basePackages = "com.whoz_in")
public class JpaConfig {
}