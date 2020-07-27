package com.alexcode.photoapp.api.gateway.PhotoAppZuulApiGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class PhotoAppZuulApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppZuulApiGatewayApplication.class, args);
	}

}
