package org.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MediaAgencyServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(MediaAgencyServerApplication.class, args);
	}

}
