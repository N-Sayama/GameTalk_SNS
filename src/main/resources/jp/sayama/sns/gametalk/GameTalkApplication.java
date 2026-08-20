package jp.sayama.sns.gametalk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@MapperScan("jp.sayama.sns.gametalk.repository.mybatis") 
public class GameTalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameTalkApplication.class, args);
		
	}

}
