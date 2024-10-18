package com.example.readConnect;

import java.util.HashSet;
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


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class ReadConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReadConnectApplication.class, args);
	}

    @Bean
    ApplicationRunner init(UserRepository userRepository, BookRepository bookRepository, IntrestRepository intrestRepository,
                        PasswordEncoder passwordEncoder) {
        return args -> {
            // Create and save Users
            List<User> users = IntStream.rangeClosed(1, 5)
                    .mapToObj(i -> new User("user" + i, passwordEncoder.encode("pass" + i), "user" + i + "@example.com", "Country" + i,  i, false, new HashSet<>()))
                    .map(userRepository::save)
                    .peek(user -> {
//                        Intrest intrest = new Intrest("Intrest for " + user.getUsername(), user);
                        Intrest intrest = new Intrest("general");
                        intrestRepository.save(intrest);
                        user.getIntrests().add(intrest);
                        userRepository.save(user); // Update user with the new skill
                    })
                    .collect(Collectors.toList());
            
            Random random = new Random();

   
         // Create and save Books
            IntStream.rangeClosed(1, 5).forEach(i -> {
                Book book = new Book(users.get(i - 1), 
                                     new HashSet<>(), 
                                     ("Title" + i), 
                                     "Author" + i, 
                                     "ISBN" + i, 
                                     null); // assuming no image for now
                bookRepository.save(book);
            });           
        
        };
    }
}
