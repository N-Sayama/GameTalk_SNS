package jp.sayama.sns.gametalk.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.sayama.sns.gametalk.model.LoginUser;

@Mapper
public interface LoginUserMapper {

    // ユーザー名で1件取得
    LoginUser findByUserName(@Param("username") String username);

    // 登録
    void register(LoginUser loginUser);

    // 指定ユーザー以外を全件取得
    List<LoginUser> findAllExcept(@Param("username") String username);
}