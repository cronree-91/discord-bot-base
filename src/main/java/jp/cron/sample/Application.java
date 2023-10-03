package jp.cron.sample;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.getPropertySources().addLast(new SettingsServerPropertySource());
        new SpringApplicationBuilder(Application.class)
                .environment(environment)
                .web(WebApplicationType.NONE).run(args);
    }

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }

}
