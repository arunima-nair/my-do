package com.dt;

import com.dt.deliveryorder.util.FileMappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DoFileProcessingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DoFileProcessingApplication.class, args);
	}

	@Autowired
	FileMappingUtil fileMappingUtil;
	@Override
	public void run(String... args) throws Exception {

		fileMappingUtil.getLineMap("ONE");

	}
}
