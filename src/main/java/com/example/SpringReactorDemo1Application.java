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
				.log()
				.map(String::toUpperCase);

		upper.subscribe(new Subscriber<String>() {

			private int count = 0;
			private Subscription subscription;

			@Override
			public void onSubscribe(Subscription subscription) {
				this.subscription = subscription;
				subscription.request(2);
			}

			@Override
			public void onNext(String t) {
				System.out.println("onNext: "+t);
				count++;
				if (count>=2) {
					count = 0;
					subscription.request(2);
				}
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

		//upper.subscribe(2);

	}
}
