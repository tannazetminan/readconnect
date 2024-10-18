package com.example.readConnect.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
	@JsonManagedReference
    private User writer;
	
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Intrest> intrests = new HashSet<>();
    
    @Lob
    private byte[] image;
	
    private String title;
    private String author;
    private String isbn;
    private String location;  // For rare book tracking
    private double averageRating;
    private int totalRatings;
    private int soldNum;
    
    
    // Constructors
    public Book() {	}
    
	public Book(User writer, Set<Intrest> intrests,  String title, String author, String isbn, byte[] image) {
		this.writer = writer;
		this.intrests = intrests;
		this.image = image;
		this.title = title;
		this.author = author;
		this.isbn = isbn;
	}


	public Book(User writer, Set<Intrest> intrests, byte[] image, String title, String author, String isbn,
			String location, double averageRating, int totalRatings, int soldNum) {
		this.writer = writer;
		this.intrests = intrests;
		this.image = image;
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.location = location;
		this.averageRating = averageRating;
		this.totalRatings = totalRatings;
		this.soldNum = soldNum;
	}    
    

	// Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getWriter() {
		return writer;
	}
	public void setWriter(User writer) {
		this.writer = writer;
	}
	public Set<Intrest> getIntrests() {
		return intrests;
	}
	public void setIntrests(Set<Intrest> intrests) {
		this.intrests = intrests;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public double getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
	public int getTotalRatings() {
		return totalRatings;
	}
	public void setTotalRatings(int totalRatings) {
		this.totalRatings = totalRatings;
	}
	public int getSoldNum() {
		return soldNum;
	}
	public void setSoldNum(int soldNum) {
		this.soldNum = soldNum;
	}
	
}
