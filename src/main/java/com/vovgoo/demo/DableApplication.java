package com.vovgoo.demo;

import com.vovgoo.demo.config.properties.CaptchaProperties;
import com.vovgoo.demo.config.properties.JwtProperties;
import com.vovgoo.demo.config.properties.MailProperties;
import com.vovgoo.demo.config.properties.RegistrationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableConfigurationProperties ({
		JwtProperties.class,
		MailProperties.class,
		CaptchaProperties.class,
		RegistrationProperties.class
})
@EnableAsync
@EnableJpaAuditing
@SpringBootApplication
public class DableApplication {

	public static void main(String[] args) {
		SpringApplication.run(DableApplication.class, args);
	}

}
