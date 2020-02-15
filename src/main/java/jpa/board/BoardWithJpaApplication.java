package jpa.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class BoardWithJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardWithJpaApplication.class, args);
	}
}
