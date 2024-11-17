package com.example.readConnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.readConnect.model.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestParam Long userId, @RequestParam Long bookId, @RequestParam String content) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found."));

        Comment comment = new Comment(user, book, content);
        return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<List<Comment>> getCommentsByBook(@PathVariable Long bookId) {
        List<Comment> comments = commentRepository.findByBookId(bookId);
        return comments.isEmpty() ? 
               new ResponseEntity<>(HttpStatus.NOT_FOUND) : 
               new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
