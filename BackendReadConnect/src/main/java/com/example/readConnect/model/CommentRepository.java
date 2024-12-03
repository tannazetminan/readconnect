package com.example.readConnect.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBookId(Long bookId);
    
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.book.id = :bookId")
    long countCommentsByBookId(@Param("bookId") Long bookId);

    @Query("SELECT MAX(c.timestamp) FROM Comment c WHERE c.book.id = :bookId")
    LocalDateTime findLastCommentTimestampByBookId(@Param("bookId") Long bookId);
}
