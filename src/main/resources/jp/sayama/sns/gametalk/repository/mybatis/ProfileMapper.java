package jp.sayama.sns.gametalk.repository.mybatis;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Primary;

import jp.sayama.sns.gametalk.model.Profile;
@Primary
@Mapper
public interface ProfileMapper {
	
	List<Profile>findAll();
	List<Profile> findByNameContaining(String keyword);
	
	Optional<Profile>findById(long Id);
	Optional<Profile> findByUserName(String userName);

	
	void register(Profile Profile);
	
	void update(Profile Profile);
	
	void delete(Profile Profile);

}

