package org.cs3219.project.peerprep;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.cs3219.project.peerprep.mapper")
public class PeerprepApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeerprepApplication.class, args);
	}

}
