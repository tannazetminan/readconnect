package com.example.readConnect.model;

public class MessageDTO {
    private Long senderId;
    private Long receiverId;
    private String content;

    // Getters and setters
    public Long getSenderId() { return senderId; }

    public void setSenderId(Long senderId) { this.senderId = senderId; }

    public Long getReceiverId() { return receiverId; }

    public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }
}


//public class MessageDTO {
//    private Long userId;
//    private Long user2Id;
//    private String content;
//
//    // Getters and setters
//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public Long getUser2Id() {
//        return user2Id;
//    }
//
//    public void setUser2Id(Long user2Id) {
//        this.user2Id = user2Id;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//}
