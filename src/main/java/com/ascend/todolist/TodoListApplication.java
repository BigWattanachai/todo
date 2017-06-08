package com.ascend.todolist;

import com.ascend.todolist.services.ConfigurationService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaAuditing
public class TodoListApplication {

    private ConfigurationService configurationService;

    private MyConfig myConfig;

    @Autowired
    public TodoListApplication(ConfigurationService configurationService, MyConfig myConfig) {
        this.configurationService = configurationService;
        this.myConfig = myConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(TodoListApplication.class, args);
    }

    @Value("${spring.configImageURL}")
    String configImageUrl;

    @Value("${spring.my.secret}")
    String mySecret;

    @PostConstruct
    void init() {
        System.out.println("=== ConfigurationService =====");
        if (configurationService.getDemoNumberMap() != null) {
            configurationService.getDemoNumberMap()
                    .forEach((k, v) -> System.out.println("Key : " + k + " Value : " + v));
        }
        System.out.println("=================");
        System.out.println("=== @Value =====");
        System.out.println(configImageUrl);
        System.out.println(mySecret);
        System.out.println("=================");
        System.out.println("=== MyConfig =====");
        System.out.println(myConfig.getNumber());
        System.out.println(myConfig.getUuid());
        System.out.println("==================");
    }
}

@ConfigurationProperties(prefix = "spring.my")
@Configuration

@Data
class MyConfig {
    String secret;
    String number;
    String uuid;
}







