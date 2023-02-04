package br.ufpr.tads.msbantadsauth;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@EnableRabbit
@EnableMongoRepositories
public class MsBantadsAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBantadsAuthApplication.class, args);
	}

}
