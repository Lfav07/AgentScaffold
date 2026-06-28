package com.lfav07.agentscaffold;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AgentscaffoldApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgentscaffoldApplication.class, args);
	}

}
