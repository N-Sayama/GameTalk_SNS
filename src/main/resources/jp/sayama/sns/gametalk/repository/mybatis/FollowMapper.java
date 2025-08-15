package jp.sayama.sns.gametalk.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FollowMapper {

    void follow(@Param("follower") String follower, @Param("followed") String followed);

    void unfollow(@Param("follower") String follower, @Param("followed") String followed);

    boolean isFollowing(@Param("follower") String follower, @Param("followed") String followed);

    List<String> findFollowers(@Param("userName") String userName);

    List<String> findFollowing(@Param("userName") String userName);
    
    
    int countFollowers(@Param("userName") String userName);
    int countFollowing(@Param("userName") String userName);

}