package com.ocal.medheadsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class MedheadsecurityApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MedheadsecurityApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("DONE INITIALIZING");	
	}

}
