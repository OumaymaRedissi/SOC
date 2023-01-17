package com.example.subscriberservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.sql.Date;

@SpringBootApplication
public class SubscriberServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubscriberServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(SubscriberRepository subscriberRepository,
							RepositoryRestConfiguration restConfiguration){
		return args->{
			restConfiguration.exposeIdsFor(Subscriber.class);
			subscriberRepository.save(new Subscriber(null,"oumaymaR@gmail.com","123456","Oumayma","Redissi","Rades",new Date(1998,03,02)));
			subscriberRepository.save(new Subscriber(null,"oumaymaS@gmail.com","123456","Oumaima","Saidi","Rades",new Date(1998,03,02)));
			subscriberRepository.save(new Subscriber(null,"Lina@gmail.com","123456","lina","Redissi","Rades", new Date(1998,03,02)));
		};
	}
}
