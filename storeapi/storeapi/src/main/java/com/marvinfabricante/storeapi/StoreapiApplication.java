package com.marvinfabricante.storeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreapiApplication.class, args);
		/* just like express */
		System.out.println("Yung server moh is running at port http://localhost:3000/api/products/");
	}

}
