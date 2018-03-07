package org.yeshm.democlient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients // 用于启动Feign功能
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class);
	}
}
