package br.fai.vl.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "br.fai.vl.*" })
public class VlApiApplication {

	public static void main(final String[] args) {
		SpringApplication.run(VlApiApplication.class, args);
	}

}
