package jp.sayama.sns.gametalk.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.sayama.sns.gametalk.model.Message;
import jp.sayama.sns.gametalk.repository.mybatis.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public void sendMessage(String sender, String recipient, String content) {
        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());

        messageRepository.save(message);
    }

    public List<Message> getMessagesBetweenUsers(String sender, String recipient) {
        return messageRepository.findMessagesBetweenUsers(sender, recipient);
    }
}