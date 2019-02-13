package eu.activageproject.datalake.config;

import eu.activageproject.datalake.aop.logging.LoggingAspect;

import io.github.simlife.config.SimlifeConstants;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(SimlifeConstants.SPRING_PROFILE_DEVELOPMENT)
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}
