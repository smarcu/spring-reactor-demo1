package com.example;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class SpringReactorDemo1Application {

	public static void main(String[] args) throws Exception {


		Flux.just("red", "white", "blue")
			.log()
			.map(String::toUpperCase)

			// subscriptions to be handled on background thread
			.subscribeOn(Schedulers.parallel())

			.subscribe(s -> {System.out.println(s);}, 2);

		// give schedulers a chance to finish
		Thread.sleep(2000);
	}
}
