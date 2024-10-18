package com.example.readConnect.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "Intrests")
public class Intrest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @JsonIgnore
    private Book book;

	public Intrest() {}

	public Intrest(String name) {
		this.name = name;
	}

	public Intrest(String name, User user) {
		this.name = name;
		this.user = user;
	}
	
	
	public Intrest(String name, Book book ) {
		this.name = name;
		this.book = book;
		
	}
	
	public Intrest(String name, User user, Book book ) {
		this.name = name;
		this.user = user;
		this.book =book;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
    
}
