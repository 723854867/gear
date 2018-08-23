package org.gear.common;

import org.jupiter.dispatcher.ThreadFakeDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GearInitializer {

	@Bean
	public ThreadFakeDispatcher threadFakeDispatcher() {
		return new ThreadFakeDispatcher();
	}
}
