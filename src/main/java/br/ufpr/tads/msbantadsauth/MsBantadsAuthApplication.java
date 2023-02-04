package br.ufpr.tads.msbantadsauth;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, SecurityAutoConfiguration.class})
@EnableRabbit
public class MsBantadsAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBantadsAuthApplication.class, args);
	}

}
