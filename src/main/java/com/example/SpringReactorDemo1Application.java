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
			.map(String::toUpperCase)
			.subscribeOn(Schedulers.newParallel("sub"))

			// Flux also has a publishOn() method which is the same, but for the listeners
			// (i.e. onNext() or consumer callbacks) instead of for the subscriber itself

			.publishOn(Schedulers.newParallel("pub"), 3)

			//Notice that the consumer callbacks (logging "Consumed: …​") are on the publisher thread pub-1-1.
			// If you take out the subscribeOn() call, you might see all of the 2nd chunk of data processed
			// on the pub-1-1 thread as well. This, again, is Reactor being frugal with threads 
			// — if there’s no explicit request to switch threads it stays on the same one for the next call,
			// whatever that is.

			.subscribe(value -> {LOG.info("Consumed: {}", value);});


		// give schedulers a chance to finish
		Thread.sleep(2000);
	}
}
