package com.bridee.api.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("services.bridee.email")
public class EmailProperties {

    private String host;
    private String user;
    private String password;

}
