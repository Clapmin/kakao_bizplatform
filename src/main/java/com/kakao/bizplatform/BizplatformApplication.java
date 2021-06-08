package com.kakao.bizplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BizplatformApplication {
	public static void main(String[] args) {
		SpringApplication.run(BizplatformApplication.class, args);
	}
}
