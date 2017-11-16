package ca.com.br.robotchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RobotChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RobotChallengeApplication.class, args);
	}
}

@RestController
class MainController {

	@RequestMapping("/v1/api")
	public String index() {
		return "Robot Challange API";
	}
}

