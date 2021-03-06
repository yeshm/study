package org.yeshm.democlient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableDiscoveryClient // 用于启动服务发现功能
@EnableFeignClients // 用于启动Fegin功能
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class);
	}
}
