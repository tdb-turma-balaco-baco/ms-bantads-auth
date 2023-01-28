package br.ufpr.tads.msbantadsauth;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class MsBantadsAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBantadsAuthApplication.class, args);
	}

}
