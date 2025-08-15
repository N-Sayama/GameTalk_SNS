package jp.sayama.sns.gametalk.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.sayama.sns.gametalk.model.Post;

@Mapper
public interface PostMapper {
	void insert(Post post);
    List<Post> findAll();
}
