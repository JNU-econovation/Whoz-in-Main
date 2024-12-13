package com.whoz_in.log_writer.system_validator;

import com.whoz_in.log_writer.common.SystemNetworkInterfaces;
import com.whoz_in.log_writer.config.NetworkConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//TODO: Conditional로 뺄지 아니면
@Slf4j
@Configuration
public class SystemValidatorConfig {
    private final String profile;
    private final NetworkConfig config;
    private final SystemNetworkInterfaces systemNIs;
    private final CommandInstalledValidator commandInstalledValidator;
    private final NetworkInterfaceValidator networkInterfaceValidator;

    public SystemValidatorConfig(@Value("${spring.profiles.active}") String profile,
            NetworkConfig config,
            SystemNetworkInterfaces systemNIs,
            CommandInstalledValidator commandInstalledValidator,
            NetworkInterfaceValidator networkInterfaceValidator) {
        this.profile = profile;
        this.config = config;
        this.systemNIs = systemNIs;
        this.commandInstalledValidator = commandInstalledValidator;
        this.networkInterfaceValidator = networkInterfaceValidator;
    }

    @Bean
    public SystemValidator systemValidator(){
        String osName = System.getProperty("os.name").toLowerCase();
        log.info("운영체제 - {}", osName);
        log.info("스프링 프로필 - {}", profile);

        if (!profile.equals("prod") || !osName.contains("nux")){
            log.info("리눅스가 아니거나 스프링 프로필이 prod가 아니므로 시스템 검증을 수행하지 않습니다.");
            return null;
        }
        return new SystemValidator(config, systemNIs, commandInstalledValidator, networkInterfaceValidator);
    }
}
