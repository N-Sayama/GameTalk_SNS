package jp.sayama.sns.gametalk.repository;

import java.util.Optional;

import jp.sayama.sns.gametalk.model.LoginUser;

public interface LoginUserRepository {
	/**
	* ユーザー名からログインユーザーを検索する。
	*
	* @param userName ユーザー名
	* @return ログインユーザー
	*/
	Optional<LoginUser> findByUserName(String userName);

	/**
	* ログインユーザーを登録する。
	*
	* @param loginUser ログインユーザー
	*/
	void register(LoginUser loginUser);

}