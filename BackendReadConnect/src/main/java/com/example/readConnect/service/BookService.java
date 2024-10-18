package com.example.readConnect.service;

import com.example.readConnect.model.*;
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
}