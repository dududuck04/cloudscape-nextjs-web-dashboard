package com.stc.exporter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(
//		exclude = {
//		SecurityAutoConfiguration.class, // spring Secuirty 자동 비밀번호 설정 제외
//		ManagementWebSecurityAutoConfiguration.class } // Actuator 보안 설정 제외
)
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

