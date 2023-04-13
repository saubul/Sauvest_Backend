package sauprojects.sauvest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SauvestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SauvestApplication.class, args);
	}

}
