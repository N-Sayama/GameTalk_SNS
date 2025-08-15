package jp.sayama.sns.gametalk.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import jp.sayama.sns.gametalk.model.LoginUser;
import jp.sayama.sns.gametalk.repository.mybatis.LoginUserMapper;

@Repository
public class DatabaseLoginUserRepositoryImpl implements LoginUserRepository {

	
	private final LoginUserMapper loginUserMapper;

	
	public DatabaseLoginUserRepositoryImpl(LoginUserMapper loginUserMapper) {
		// ログインユーザーのマッパーを初期化する。
		this.loginUserMapper = loginUserMapper;
	}

	@Override
	public Optional<LoginUser> findByUserName(String userName) {
	    return Optional.ofNullable(loginUserMapper.findByUserName(userName));
	}

	@Override
	public void register(LoginUser loginUser) {
		loginUserMapper.register(loginUser);
	}
}