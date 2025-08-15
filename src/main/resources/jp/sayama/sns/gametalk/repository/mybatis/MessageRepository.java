package jp.sayama.sns.gametalk.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.sayama.sns.gametalk.model.Message;

@Mapper
public interface MessageRepository {
    void save(Message message);

    List<Message> findMessagesBetweenUsers(
    		@Param("sender") String sender, @Param("recipient") String recipient);
}