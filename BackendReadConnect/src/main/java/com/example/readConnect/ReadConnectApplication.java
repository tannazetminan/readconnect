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
			MessageRepository messageRepository, CommentRepository commentRepository, PasswordEncoder passwordEncoder) {
		return args -> {

			String[] category = { "General", "Psychology", "Science", "Historical", "Literature" };
			String[] names = { "John Smiths", "Sarah Nick", "Tom Swift", "Maria Debroh", "Sam Jackson" };

			
			// Create and save Users
			List<User> users = IntStream.rangeClosed(1, 5)
					.mapToObj(i -> new User(names[i-1], passwordEncoder.encode("pass" + i), "user" + i + "@example.com",
							"Country" + i, i, false, category[i - 1]))
					.map(userRepository::save).collect(Collectors.toList());

			Random random = new Random();

			
			// Add real books for each user
	        List<Book> books = List.of(
	                //(General)
	                new Book(users.get(0), "A comprehensive guide to artificial intelligence.", "General",
	                        "A Guide to Intelligent Systems", "Michael Negnevitsky",
	                        "9781408225745", "San Francisco", 0, 0, 200, LocalDateTime.now().minusDays(10), null),
	                new Book(users.get(0), "Exploring human evolution through genetics.", "General",
	                        "Sapiens: A Brief History of Humankind", "Yuval Noah Harari",
	                        "9780062316110", "New York", 200, 50, 300, LocalDateTime.now().minusDays(15), null),
	                new Book(users.get(0), "Learn the basics of programming with Python.", "General",
	                        "Python Crash Course", "Eric Matthes", "9781593279288",
	                        "Seattle", 20, 20, 150, LocalDateTime.now().minusDays(200), null),
	                new Book(users.get(0), "An overview of economic theories.", "General",
	                        "The Wealth of Nations", "Adam Smith", "9781505577129",
	                        "London", 120, 30, 100, LocalDateTime.now().minusDays(450), null),
	                new Book(users.get(0), "Learn leadership through inspiring stories.", "General",
	                        "Leaders Eat Last", "Simon Sinek", "9781591848011",
	                        "Toronto", 45, 15, 180, LocalDateTime.now().minusDays(3), null),
               
	                
	                // User 2 (Psychology)
	                new Book(users.get(1), "How to influence people in social settings.", "Psychology",
	                        "How to Win Friends and Influence People", "Dale Carnegie", "9780671027032",
	                        "Chicago", 50, 25, 350, LocalDateTime.now().minusDays(120), null),
	                new Book(users.get(1), "Understanding mental health and emotions.", "Psychology",
	                        "Emotional Intelligence", "Daniel Goleman", "9780553383713",
	                        "Los Angeles", 100, 20, 270, LocalDateTime.now().minusDays(8), null),
	                new Book(users.get(1), "The psychology of decision-making.", "Psychology",
	                        "Thinking, Fast and Slow", "Daniel Kahneman", "9780374533557",
	                        "Boston", 90, 30, 210, LocalDateTime.now().minusDays(60), null),
	                new Book(users.get(1), "Exploring the roots of happiness.", "Psychology",
	                        "The Happiness Advantage", "Shawn Achor", "9780307591548",
	                        "Denver", 30, 20, 190, LocalDateTime.now().minusDays(400), null),
	                new Book(users.get(1), "How habits shape our lives.", "Psychology",
	                        "Atomic Habits", "James Clear", "9780735211292",
	                        "San Diego", 50, 30, 400, LocalDateTime.now().minusDays(2), null),

	                // User 3 (Science)
	                new Book(users.get(2), "A beginner's guide to the cosmos.", "Science",
	                        "Astrophysics for People in a Hurry", "Neil deGrasse Tyson", "9780393609394",
	                        "Washington DC", 60, 30, 500, LocalDateTime.now().minusDays(300), null),
	                new Book(users.get(2), "A journey through time and space.", "Science",
	                        "A Brief History of Time", "Stephen Hawking", "9780553380163",
	                        "Cambridge", 100, 35, 520, LocalDateTime.now().minusDays(10), null),
	                new Book(users.get(2), "How biology impacts daily life.", "Science",
	                        "The Selfish Gene", "Richard Dawkins", "9780198788607",
	                        "Oxford", 15, 3, 440, LocalDateTime.now().minusDays(150), null),
	                new Book(users.get(2), "Learn the wonders of chemistry.", "Science",
	                        "The Disappearing Spoon", "Sam Kean", "9780316051644",
	                        "Philadelphia", 10, 2, 380, LocalDateTime.now().minusDays(120), null),
	                new Book(users.get(2), "A fascinating guide to the human body.", "Science",
	                        "The Body: A Guide for Occupants", "Bill Bryson", "9780385539302",
	                        "Dallas", 3, 3, 500, LocalDateTime.now().minusDays(25), null),

	                // User 4 (Historical)
	                new Book(users.get(3), "A history of the rise of civilizations.", "Historical",
	                        "Guns, Germs, and Steel", "Jared Diamond", "9780393317558",
	                        "Rome", 50, 15, 300, LocalDateTime.now().minusDays(460), null),
	                new Book(users.get(3), "The story of World War II.", "Historical",
	                        "The Second World War", "Antony Beevor", "9780143124931",
	                        "Berlin", 46, 10, 250, LocalDateTime.now().minusDays(12), null),
	                new Book(users.get(3), "The Roman Empire's greatest moments.", "Historical",
	                        "The Decline and Fall of the Roman Empire", "Edward Gibbon", "9780140437645",
	                        "Athens", 20, 12, 200, LocalDateTime.now().minusDays(150), null),
	                new Book(users.get(3), "A tale of power and intrigue.", "Historical",
	                        "The Silk Roads: A New History of the World", "Peter Frankopan", "9781101912379",
	                        "Istanbul", 5, 2, 280, LocalDateTime.now().minusDays(18), null),
	                new Book(users.get(3), "A deep dive into medieval Europe.", "Historical",
	                        "A Distant Mirror", "Barbara Tuchman", "9780345349576",
	                        "Paris", 1, 1, 150, LocalDateTime.now().minusDays(200), null),

	                // User 5 (Literature)
	                new Book(users.get(4), "A classic tale of love and sacrifice.", "Literature",
	                        "The Great Gatsby", "F. Scott Fitzgerald", "9780743273565",
	                        "New York", 60, 25, 350, LocalDateTime.now().minusDays(80), null),
	                new Book(users.get(4), "A powerful story about justice.", "Literature",
	                        "To Kill a Mockingbird", "Harper Lee", "9780061120084",
	                        "Alabama", 100, 30, 500, LocalDateTime.now().minusDays(410), null),
	                new Book(users.get(4), "The struggles of the Bennet sisters.", "Literature",
	                        "Pride and Prejudice", "Jane Austen", "9781503290563",
	                        "London", 80, 20, 450, LocalDateTime.now().minusDays(12), null),
	                new Book(users.get(4), "An epic journey across time and space.", "Literature",
	                        "One Hundred Years of Solitude", "Gabriel García Márquez", "9780060883287",
	                        "Colombia", 5, 1, 400, LocalDateTime.now().minusDays(314), null),
	                new Book(users.get(4), "The life and struggles of Anna Karenina.", "Literature",
	                        "Anna Karenina", "Leo Tolstoy", "9780143035008",
	                        "Moscow", 2, 1, 300, LocalDateTime.now().minusDays(16), null)
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
			
	        
	        
	        // Add comments for books
	        for (Book book : books) {
	            // Add 3-5 comments for each book
	            IntStream.range(0, random.nextInt(3) + 3).forEach(i -> {
	                User commenter = users.get(random.nextInt(users.size())); // Random user
	                String content = "This is a great book about " + book.getCategory().toLowerCase() + ".";
	                LocalDateTime timestamp = LocalDateTime.now().minusDays(random.nextInt(30)); // Random timestamp
	                Comment comment = new Comment(commenter, book, content, timestamp);
	                commentRepository.save(comment);
	            });
	        }
	        
		};
	}
}
