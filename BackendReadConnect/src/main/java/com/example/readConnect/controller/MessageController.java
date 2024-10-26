package com.example.readConnect.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.readConnect.model.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;


	// Send a new message
    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody MessageDTO messageDTO) {
        User sender = userRepository.findById(messageDTO.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(messageDTO.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Message message = new Message(sender, receiver, messageDTO.getContent(), LocalDateTime.now());
        Message savedMessage = messageRepository.save(message);
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }

    // Retrieve messages between sender and receiver
//    @GetMapping("/{senderId}/to/{receiverId}")
//    public ResponseEntity<List<Message>> getMessagesBetweenUsers(@PathVariable Long senderId, @PathVariable Long receiverId) {
//        User sender = userRepository.findById(senderId)
//                .orElseThrow(() -> new RuntimeException("Sender not found"));
//        User receiver = userRepository.findById(receiverId)
//                .orElseThrow(() -> new RuntimeException("Receiver not found"));
//
//        List<Message> messages = messageRepository.findBySenderAndReceiver(sender, receiver);
//        return new ResponseEntity<>(messages, HttpStatus.OK);
//    }

    
    @GetMapping("/{senderId}/to/{receiverId}")
    public ResponseEntity<List<Message>> getMessagesBetweenUsers(@PathVariable Long senderId, @PathVariable Long receiverId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // Fetch messages where sender is the sender and receiver is the receiver
        List<Message> sentMessages = messageRepository.findBySenderAndReceiver(sender, receiver);
        // Fetch messages where receiver is the sender and sender is the receiver
        List<Message> receivedMessages = messageRepository.findBySenderAndReceiver(receiver, sender);

        // Combine both lists
        List<Message> allMessages = new ArrayList<>(sentMessages);
        allMessages.addAll(receivedMessages);

        // Sort by timestamp to get a chronological order
        allMessages.sort(Comparator.comparing(Message::getTimestamp));

        return new ResponseEntity<>(allMessages, HttpStatus.OK);
    }
    
//    @GetMapping("/conversations/{userId}")
//    public ResponseEntity<?> getUserConversations(@PathVariable Long userId) {
//        User user = userRepository.findById(userId)
//            .orElseThrow(() -> new RuntimeException("User not found"));
//        
//        List<User> usersInConversation = messageRepository.findDistinctUsersInConversation(userId);
//        return ResponseEntity.ok(usersInConversation);
//    }

    
//    @PostMapping("/send")
//    public ResponseEntity<Message> sendMessage(@RequestBody MessageDTO messageDTO) {
//        User user = userRepository.findById(messageDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
//        User user2 = userRepository.findById(messageDTO.getUser2Id()).orElseThrow(() -> new RuntimeException("User2 not found"));
//
//        Message message = new Message();
//        message.setUser(user);
//        message.setUser2(user2);
//        message.setContent(messageDTO.getContent());
//        message.setTimestamp(LocalDateTime.now());
//
//        Message savedMessage = messageRepository.save(message);
//        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
//    }


//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<Message>> getMessagesForUser(@PathVariable Long userId, @RequestParam Long otherUserId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        User otherUser = userRepository.findById(otherUserId).orElseThrow(() -> new RuntimeException("Other user not found"));
//
//        List<Message> messages = messageRepository.findMessagesBetweenUsers(user.getId(), otherUser.getId());
//
//        return ResponseEntity.ok(messages);
//    }
    
//    @GetMapping("/user/{userId}")
//    public List<Message> getMessagesForUser(@PathVariable Long userId, @RequestParam Long user2Id) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        User user2 = userRepository.findById(user2Id).orElseThrow(() -> new RuntimeException("user2 not found"));
//        
//        // get messages where user is the sender and user2 is the receiver
//        List<Message> messagesFromUserToWorker = messageRepository.findByUserAndUser2(user, user2);
//
//        List<Message> allMessages = new ArrayList<>();
//        allMessages.addAll(messagesFromUserToWorker);
//        
//        return allMessages;
//    }

//
//    @GetMapping("/worker/{workerId}")
//    public List<Message> getMessagesForWorker(@PathVariable Long workerId, @RequestParam Long userId) {
//        Worker worker = workerRepository.findById(workerId).orElseThrow(() -> new RuntimeException("Worker not found"));
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//
//        // get messages where worker is the sender and user is the receiver
//        List<Message> messagesFromWorkerToUser = messageRepository.findByWorkerAndUser(worker, user);
//
//        List<Message> allMessages = new ArrayList<>();
//        allMessages.addAll(messagesFromWorkerToUser);
//
//        return allMessages;
//    }


//    @GetMapping("/user/{userId}/all")
//    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//
//        List<Message> messages = messageRepository.findByUser(user);
//
//        if (messages.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(messages, HttpStatus.OK);
//    }

//    @GetMapping("/worker/{workerId}/all")
//    public ResponseEntity<List<Message>> getAllMessagesByWorker(@PathVariable Long workerId) {
//        Worker worker = workerRepository.findById(workerId).orElseThrow(() -> new RuntimeException("Worker not found"));
//
//        List<Message> messages = messageRepository.findByWorker(worker);
//
//        if (messages.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(messages, HttpStatus.OK);
//    }

}
