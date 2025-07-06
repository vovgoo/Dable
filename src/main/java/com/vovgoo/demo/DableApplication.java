package com.vovgoo.demo;

import com.vovgoo.demo.config.CaptchaProperties;
import com.vovgoo.demo.config.JwtProperties;
import com.vovgoo.demo.config.MailProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties ({
		JwtProperties.class,
		MailProperties.class,
		CaptchaProperties.class,
})
@SpringBootApplication
@EnableJpaAuditing
public class DableApplication {

	public static void main(String[] args) {
		SpringApplication.run(DableApplication.class, args);
	}

}
