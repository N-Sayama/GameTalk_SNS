package jp.sayama.sns.gametalk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import jp.sayama.sns.gametalk.model.Profile;
import jp.sayama.sns.gametalk.repository.mybatis.ProfileMapper;

@Repository
@Primary
public class DatabaseProfileRepositoryImpl implements ProfileRepository {
	
	private final ProfileMapper profileMapper;

	
	
	public DatabaseProfileRepositoryImpl(ProfileMapper taskMapper) {
	
	this.profileMapper = taskMapper;
	}

	@Override
	public List<Profile> findAll() {
	return profileMapper.findAll();
	}

	@Override
	public Optional<Profile> findById(Long Id) {
	return profileMapper.findById(Id);
	}

	@Override
	public void register(Profile profile) {
	profileMapper.register(profile);
	}

	@Override
	public void update(Profile profile){
	profileMapper.update(profile);
	}

	@Override
	public void delete(Profile profile) {
	profileMapper.delete(profile);
	}
	@Override
	public Optional<Profile> findByUserName(String userName) {
	    return profileMapper.findByUserName(userName);
	}
	
	@Override
	public List<Profile> findByNameContaining(String keyword) {
	    return profileMapper.findByNameContaining(keyword);
	}


}
