package org.user.api.userchamomile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class UserChamomileApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserChamomileApplication.class, args);
    }


}
