package jp.sayama.sns.gametalk.model;

import java.time.LocalDateTime;

public class Message {
    private Long id;
    private String sender;      // 送信者のユーザー名
    private String recipient;   // 受信者のユーザー名
    private String content;
    private LocalDateTime timestamp;

    // getter・setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}