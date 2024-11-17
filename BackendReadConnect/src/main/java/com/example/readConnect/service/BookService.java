package com.example.readConnect.service;

import com.example.readConnect.model.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private RatingRepository ratingRepository; 

   
    @Transactional
    public Book createBook(Long writertId,  Book bookDetails) {
        User writer = userRepository.findById(writertId).orElseThrow(() -> new RuntimeException("writer not found."));

        // set writer for the book
        bookDetails.setWriter(writer);

        // add book to user's book list
        //user.getBooks().add(bookDetails);

        // save the book
        Book savedBook = bookRepository.save(bookDetails);

        // send book creation notification
        notificationService.sendBookCreationNotification(writer, savedBook);

        return savedBook;
    }
    
    
    @Transactional
    public Book rateBook(Long bookId, Long userId, double rating) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found."));

        // Check if the user has already rated the book
        Optional<Rating> existingRating = ratingRepository.findByUserIdAndBookId(userId, bookId);
        if (existingRating.isPresent()) {
            throw new RuntimeException("User has already rated this book.");
        }

        // Save the rating
        Rating newRating = new Rating(user, book, rating);
        ratingRepository.save(newRating);

        // Update book's rating details
        book.setTotalRatingScore(book.getTotalRatingScore() + rating);
        book.setRatingCount(book.getRatingCount() + 1);
        return bookRepository.save(book);
    }
    
//    @Transactional
//    public Book addRating(Long bookId, double rating) {
//        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found."));
//        book.setTotalRatingScore(book.getTotalRatingScore() + rating);
//        book.setRatingCount(book.getRatingCount() + 1);
//        bookRepository.save(book);
//        return book;
//    }
}