package jp.sayama.sns.gametalk.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import jp.sayama.sns.gametalk.model.User;

@Mapper
public interface UserMapper {

	@Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT * FROM users WHERE username <> #{username}")
    List<User> findAllExcept(String username);
}
