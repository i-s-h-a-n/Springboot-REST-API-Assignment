package com.example.springbootbackend;

import com.example.springbootbackend.repository.DowntimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages={
		"com.example.springbootbackend", "com.example.application"})
public class SpringbootBackendApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}

		@Autowired
		private DowntimeRepository downtimeRepository;

		@Override
		public void run(String... args) throws Exception {
		}
}
