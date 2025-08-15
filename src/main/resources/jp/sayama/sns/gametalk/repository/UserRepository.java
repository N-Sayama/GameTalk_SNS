package jp.sayama.sns.gametalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.sayama.sns.gametalk.model.User; 


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}