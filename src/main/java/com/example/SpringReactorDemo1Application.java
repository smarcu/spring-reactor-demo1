package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

public class SpringReactorDemo1Application {

	public static void main(String[] args) {


		Flux<String> flux = Flux.just("red", "white", "blue");

		Flux<String> upper = flux
				.log()
				.map(String::toUpperCase);

		upper.subscribe();


	}
}
