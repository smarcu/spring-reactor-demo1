package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class SpringReactorDemo1Application {

	private static final Logger LOG = LoggerFactory.getLogger(SpringReactorDemo1Application.class);

	public static void main(String[] args) throws Exception {


		Flux.just("a", "b", "c", "d", "e", "f", "g", "h", "i")

			.log()

				// To switch the processing of the individual items to separate threads
				// (up to the limit of the pool) we need to break them out into separate publishers,
				// and for each of those publishers ask for the result in a background thread.

				.flatMap(value ->
						Mono.just(value.toUpperCase()).subscribeOn(Schedulers.parallel()),
						2)

				// Notice that there are now multiple threads consuming the items, and the concurrency
				// hint in the flatMap() ensures that there are 2 items being processed at any given time,
				// as long as they are available. We see request(1) a lot because the system is trying to keep 2
				// items in the pipeline, and generally they don’t finish processing at the same time.
				// Reactor tries to be very smart here in fact, and it pre-fetches items from the
				// upstream Publisher to try to eliminate waiting time for the subscriber

				.subscribe(value -> {LOG.info("Consumed: {}", value);});


		// give schedulers a chance to finish
		Thread.sleep(2000);
	}
}
