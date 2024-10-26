package com.example.readConnect.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndReceiver(User sender, User receiver);
    List<Message> findByReceiverAndSender(User receiver, User sender);
    
//    @Query("SELECT m FROM Message m WHERE (m.user.id = :userId AND m.user2.id = :otherUserId) OR (m.user.id = :otherUserId AND m.user2.id = :userId) ORDER BY m.timestamp ASC")
//    List<Message> findMessagesBetweenUsers(@Param("userId") Long userId, @Param("otherUserId") Long otherUserId);

//    @Query("SELECT DISTINCT m.user2 FROM Message m WHERE m.user.id = :userId OR m.user2.id = :userId")
//    List<User> findDistinctUsersInConversation(@Param("userId") Long userId);
    
//  List<Message> findByUser(User user);

}


//@Repository
//public interface MessageRepository extends JpaRepository<Message, Long> {
//    List<Message> findByUserAndUser2(User user, User user2);
//    List<Message> findByUser(User user);
//}