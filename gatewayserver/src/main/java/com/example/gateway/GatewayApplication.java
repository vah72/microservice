package com.example.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {

		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("account_route", r -> r.path("/account/**")
						.filters(f -> f.rewritePath("/account/(?<segment>.*)", "/${segment}"))
						.uri("lb://ACCOUNT"))
				.route("loan_route", r -> r.path("/loan/**")
						.filters(f -> f.rewritePath("/loan/(?<segment>.*)", "/${segment}"))
						.uri("lb://LOAN"))
				.route("card_route", r -> r.path("/card/**")
						.filters(f -> f.rewritePath("/card/(?<segment>.*)", "/${segment}"))
						.uri("lb://CARD")).build();
	}

}
