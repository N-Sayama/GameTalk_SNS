package jp.sayama.sns.gametalk.repository;

import java.util.List;
import java.util.Optional;

import jp.sayama.sns.gametalk.model.Profile;


public interface ProfileRepository {
	

	List<Profile> findAll();
	List<Profile> findByNameContaining(String keyword);

	
	Optional<Profile> findById(Long id);
	Optional<Profile> findByUserName(String userName);


	void register(Profile profile);
	
	void update(Profile profile);
	
	void delete(Profile profile);

}