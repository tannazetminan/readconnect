package com.example.readConnect.service;

import com.example.readConnect.model.*;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// for sending notifications to users and workers
@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    
    
    public void sendBookCreationNotification(User user, Book book) {
        logger.info("Notification to User {}: A new book '{}' has been created.", user.getUsername(), book.getTitle());
    }
    
    
}