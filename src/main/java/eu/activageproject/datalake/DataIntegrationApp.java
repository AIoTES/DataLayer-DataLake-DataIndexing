package eu.activageproject.datalake;

import eu.activageproject.datalake.config.ApplicationProperties;
import eu.activageproject.datalake.config.DefaultProfileUtil;

import io.github.simlife.config.SimlifeConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class DataIntegrationApp {

    private static final Logger log = LoggerFactory.getLogger(DataIntegrationApp.class);

    private final Environment env;

    public DataIntegrationApp(Environment env) {
        this.env = env;
    }

    /**
     * Initializes DataIntegration.
     * <p>
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with Simlife on <a href="https://www.simlife.io/profiles/">https://www.simlife.io/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(SimlifeConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(SimlifeConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(SimlifeConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(SimlifeConstants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not " +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DataIntegrationApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        String contextPath = env.getProperty("server.servlet.contextPath");
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}{}\n\t" +
                "External: \t{}://{}:{}{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            env.getProperty("server.port"),
            contextPath != null ? contextPath : "",
            protocol,
            hostAddress,
            env.getProperty("server.port"),
            contextPath != null ? contextPath : "",
            env.getActiveProfiles());
    }
}
