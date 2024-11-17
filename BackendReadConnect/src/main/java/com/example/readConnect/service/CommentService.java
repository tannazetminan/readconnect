package com.example.readConnect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.readConnect.model.*;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public Comment addComment(Long userId, Long bookId, String content) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found."));

        if (content == null || content.trim().isEmpty()) {
            throw new RuntimeException("Comment content cannot be empty.");
        }

        Comment comment = new Comment(user, book, content);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsForBook(Long bookId) {
        return commentRepository.findByBookId(bookId);
    }
}
