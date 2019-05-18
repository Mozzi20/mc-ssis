package io.github.mozzi20;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class McSsisApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(McSsisApplication.class, args);
	}

}
