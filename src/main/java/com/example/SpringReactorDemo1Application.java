package com.example;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

public class SpringReactorDemo1Application {

	public static void main(String[] args) {


		Flux<String> flux = Flux.just("red", "white", "blue");

		Flux<String> upper = flux
				.map(String::toUpperCase);

		upper.subscribe(new Subscriber<String>() {
			@Override
			public void onSubscribe(Subscription subscription) {
				subscription.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(String t) {
				System.out.println("onNext: " + t);
			}

			@Override
			public void onError(Throwable throwable) {
				throwable.printStackTrace();
			}

			@Override
			public void onComplete() {
				System.out.println("onComplete");
			}
		});

	}
}
