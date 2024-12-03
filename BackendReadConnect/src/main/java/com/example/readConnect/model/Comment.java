package com.example.readConnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private String content;
    private LocalDateTime timestamp;

    // Constructors
    public Comment() {}

    public Comment(User user, Book book, String content) {
        this.user = user;
        this.book = book;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }
   
    public Comment(User user, Book book, String content, LocalDateTime timestamp) {
		super();
		this.user = user;
		this.book = book;
		this.content = content;
		this.timestamp = timestamp;
	}

	// Getters and Setters   
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
