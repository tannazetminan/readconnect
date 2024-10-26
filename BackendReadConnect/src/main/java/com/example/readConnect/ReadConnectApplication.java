package com.example.readConnect;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.readConnect.model.*;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ReadConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReadConnectApplication.class, args);
	}

	@Bean
	ApplicationRunner init(UserRepository userRepository, BookRepository bookRepository,
			MessageRepository messageRepository, PasswordEncoder passwordEncoder) {
		return args -> {

			String[] category = { "General", "Psychology", "Science", "Historical", "Literature" };
			
			// Create and save Users
			List<User> users = IntStream.rangeClosed(1, 5)
					.mapToObj(i -> new User("user" + i, passwordEncoder.encode("pass" + i), "user" + i + "@example.com",
							"Country" + i, i, false, category[i - 1]))
					.map(userRepository::save).collect(Collectors.toList());

			Random random = new Random();

			
			 // Real Book Data for Each User
	        List<Book> books = List.of(
	                new Book(users.get(0), "A deep dive into technology advancements.", "General",
	                        "Technology and the Future", "Ray Kurzweil", "9780393253877", null),
	                new Book(users.get(0), "A beginner's guide to modern economics", "General",
	                        "Economics 101", "Alfred Marshall", "9781537744564", null),

	                new Book(users.get(1), "An exploration of the human mind and behavior.", "Psychology",
	                        "Thinking, Fast and Slow", "Daniel Kahneman", "9780374533557", null),
	                new Book(users.get(1), "<p>How habits shape our daily lives.</p>", "Psychology",
	                        "The Power of Habit", "Charles Duhigg", "9780812981605", null),

	                new Book(users.get(2), "An insightful introduction to astrophysics.", "Science",
	                        "Astrophysics for People in a Hurry", "Neil deGrasse Tyson", "9780393609394", null),
	                new Book(users.get(2), "A journey through the universe and beyond.", "Science",
	                        "A Brief History of Time", "Stephen Hawking", "9780553380163", null),

	                new Book(users.get(3), "The fall of ancient Rome and its impact.", "Historical",
	                        "The Decline and Fall of the Roman Empire", "Edward Gibbon", "9780140437645", null),
	                new Book(users.get(3), "A comprehensive view on World War II.", "Historical",
	                        "The Second World War", "Antony Beevor", "9780143124931", null),

	                new Book(users.get(4), "The tragic love story of Jay Gatsby and Daisy Buchanan.", "Literature",
	                        "The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", null),
	                new Book(users.get(4), "The struggles of the Bennet sisters in a class-conscious society.", "Literature",
	                        "Pride and Prejudice", "Jane Austen", "9781503290563", null)
	        );
	        books.forEach(bookRepository::save);

	        // Real Message Data for Each User
	        List<Message> messages = List.of(
	                new Message(users.get(0), users.get(1), "Hey, have you read 'Thinking, Fast and Slow'?", LocalDateTime.now().minusDays(1)),
	                new Message(users.get(1), users.get(2), "I just finished 'The Power of Habit'—great insights!", LocalDateTime.now().minusHours(5)),
	                new Message(users.get(2), users.get(3), "Astrophysics is mind-blowing! Check out Tyson's book.", LocalDateTime.now().minusHours(3)),
	                new Message(users.get(3), users.get(4), "If you're into history, 'The Second World War' is a must-read.", LocalDateTime.now().minusDays(2)),
	                new Message(users.get(4), users.get(0), "Have you seen 'The Great Gatsby'? It’s a classic!", LocalDateTime.now().minusHours(4))
	        );
	        messages.forEach(message -> messageRepository.save(message));
			
	        
//			// Create and save Books
//			IntStream.rangeClosed(1, 5).forEach(i -> {
//				Book book = new Book(users.get(i - 1), "<p>lorem</p>", category[i - 1], ("Title" + i), "Author" + i,
//						"ISBN" + i, null); // assuming no image for now
//				bookRepository.save(book);
//			});
			
//            List<User> users = IntStream.rangeClosed(1, 5)
//                    .mapToObj(i -> new User("user" + i, passwordEncoder.encode("pass" + i), "user" + i + "@example.com", "Country" + i,  i, false, new HashSet<>()))
//                    .map(userRepository::save)
//                    .peek(user -> {
////                        Intrest intrest = new Intrest("Intrest for " + user.getUsername(), user);
//                        Intrest intrest = new Intrest("general");
//                        intrestRepository.save(intrest);
//                        user.getIntrests().add(intrest);
//                        userRepository.save(user); // Update user with the new skill
//                    })
//                    .collect(Collectors.toList());

//            IntStream.rangeClosed(1, 5).forEach(i -> {
//                Book book = new Book(users.get(i - 1), 
//                                     new HashSet<>(), 
//                                     ("Title" + i), 
//                                     "Author" + i, 
//                                     "ISBN" + i, 
//                                     null); // assuming no image for now
//                bookRepository.save(book);
//            });           

		};
	}
}
