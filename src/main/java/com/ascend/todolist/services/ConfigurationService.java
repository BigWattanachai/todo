package com.ascend.todolist.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * Created by BiG on 6/6/2017 AD.
 */

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "spring")
public class ConfigurationService {
    HashMap<Long, String> demoNumberMap;

}

