package com.sid.gl.manageemployee;

import com.sid.gl.manageemployee.security.SpringSecurityAuditorAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
@EnableSwagger2
public class ManageEmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageEmployeeApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorAware() {
		return new SpringSecurityAuditorAware();
	}



}
