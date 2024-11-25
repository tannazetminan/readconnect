package com.example.readConnect.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findBySenderAndReceiver(User sender, User receiver);

	List<Message> findByReceiverAndSender(User receiver, User sender);

	List<Message> findBySenderOrReceiver(User sender, User receiver);

	// Find the latest message between two users
	Message findTopBySenderAndReceiverOrderByTimestampDesc(User sender, User receiver);

	// Query to find distinct users who are in conversation with the given user
	@Query("SELECT DISTINCT m.receiver FROM Message m WHERE m.sender.id = :userId "
			+ "UNION SELECT DISTINCT m.sender FROM Message m WHERE m.receiver.id = :userId")
	List<User> findDistinctUsersInConversation(@Param("userId") Long userId);

}
