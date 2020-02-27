package ae.dt;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.WebApplicationInitializer;

import ae.dt.deliveryorder.controller.BillOfLaddingController;

@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling

public class DOApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

	private static Logger logger = LoggerFactory.getLogger(DOApplication.class);

	public static void main(String[] args) {
		//new File(BillOfLaddingController.uploadDir).mkdir();
		SpringApplication.run(DOApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DOApplication.class);
	}


}
