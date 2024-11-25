package com.example.readConnect.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.readConnect.model.*;
import com.example.readConnect.service.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;
    

    @Autowired
    private CommentService commentService;

    
    // get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> books = bookRepository.findAll();
            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(books, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get a single book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(worker -> new ResponseEntity<>(worker, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
     
    
    @PostMapping("/create")
    public ResponseEntity<Book> createBook(
            @RequestParam Long writerId, 
            @RequestParam("image") MultipartFile image, 
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String isbn,
            @RequestParam String location,
            @RequestParam String category,
            @RequestParam String description,
            @RequestParam LocalDateTime createDate) {
        try {
            Book bookDetails = new Book();
            // Convert MultipartFile to byte array
            bookDetails.setImage(image.getBytes());
            bookDetails.setTitle(title);
            bookDetails.setAuthor(author);
            bookDetails.setIsbn(isbn);
            bookDetails.setLocation(location);
            bookDetails.setCategory(category);
            bookDetails.setDescription(description);
            bookDetails.setCreateDate(createDate);

            Book createdBook = bookService.createBook(writerId, bookDetails);
            return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
          
    
    // Add rating to a book
    @PostMapping("/{bookId}/rate")
    public ResponseEntity<Book> rateBook(
            @PathVariable Long bookId, 
            @RequestParam Long userId, 
            @RequestParam double rating) {
        try {
            Book updatedBook = bookService.rateBook(bookId, userId, rating);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    
    
 // Add a comment to a book
    @PostMapping("/{bookId}/comment")
    public ResponseEntity<Comment> addComment(
            @PathVariable Long bookId, 
            @RequestParam Long userId, 
            @RequestParam String content) {
        try {
            Comment newComment = commentService.addComment(userId, bookId, content);
            return new ResponseEntity<>(newComment, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    
    // Get all comments for a specific book
    @GetMapping("/{bookId}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long bookId) {
        try {
            List<Comment> comments = commentService.getCommentsForBook(bookId);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    

    // get all books for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Book>> getBooksByUserId(@PathVariable Long userId) {
        try {
            List<Book> books = bookRepository.findByWriterId(userId);
            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

// // get book base on intrests
//    @GetMapping("/intrests/{intrest}")
//    public ResponseEntity<List<Book>> getBookByIntrests(@PathVariable String intrest){
//    	
//    	try {
//    	    List<Book> books = bookRepository.findBooksByIntrestName(intrest);
//    	    if (books.isEmpty()) {
//    	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    	    } else {
//    	        return new ResponseEntity<>(books, HttpStatus.OK);
//    	    }
//    	} catch (Exception e) {
//    	    return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
//    	}
//
//    }

}
