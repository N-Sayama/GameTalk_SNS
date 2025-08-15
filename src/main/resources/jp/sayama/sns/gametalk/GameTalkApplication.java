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
		// ↓ ここに追加するだけでOK（アプリ起動時に実行される）
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				String rawPassword = "testpass"; // ← 実際にログイン時に入力したパスワード
				String encodedPassword = "$2a$10$Dow1F4pQo5Byjv2a/7pTXOw46LgblUqkzPQ3EdehTV7IzzQxkHEHe"; // ← DBに保存されているハッシュ

				boolean matches = encoder.matches(rawPassword, encodedPassword);
				System.out.println("パスワード一致？: " + matches); // true ならOK、false なら不一致
	}

}