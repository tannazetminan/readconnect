package com.example.readConnect.model;

import java.time.LocalDateTime;
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
	
    
    private String description;
	private String category;
    private String title;
    private String author;
    private String isbn;
    private String location;  // For rare book tracking
    private double totalRatingScore;
    private int ratingCount;
    private int soldNum;
    private LocalDateTime createDate;

    
    @Lob
    private byte[] image;
    
    
    // Constructors
    public Book() {	}


    
    public Book(User writer, String description, String category, String title, String author, String isbn,
			String location, double totalRatingScore, int ratingCount, int soldNum, LocalDateTime createDate, byte[] image) {
		super();
		this.writer = writer;
		this.description = description;
		this.category = category;
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.location = location;
		this.totalRatingScore = totalRatingScore;
		this.ratingCount = ratingCount;
		this.soldNum = soldNum;
		this.createDate = createDate;
		this.image = image;
	}



	public Book(User writer, String description, String category, String title, String author, String isbn, 
    		LocalDateTime createDate, byte[] image) {
		super();
		this.description = description;
		this.writer = writer;
		this.image = image;
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.category = category;
		this.createDate = createDate;
	}
    public Book(User writer,  String description, String category, String title, String author, String isbn,
    		String location, LocalDateTime createDate, byte[] image) {
		super();
		this.description = description;
		this.writer = writer;
		this.image = image;
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.location = location;
		this.category = category;
		this.createDate = createDate;
	}
    public Book(User writer, String category, String title, String author, String isbn, String location,
			double averageRating, int totalRatings, int soldNum, LocalDateTime createDate, byte[] image) {
		super();
		this.writer = writer;
		this.image = image;
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.location = location;
		this.totalRatingScore = averageRating;
		this.ratingCount = totalRatings;
		this.soldNum = soldNum;
		this.category = category;
		this.createDate = createDate;
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
	
	public double getTotalRatingScore() {
		return totalRatingScore;
	}

	public void setTotalRatingScore(double totalRatingScore) {
		this.totalRatingScore = totalRatingScore;
	}

	public int getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}

	public int getSoldNum() {
		return soldNum;
	}
	public void setSoldNum(int soldNum) {
		this.soldNum = soldNum;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	//methods
	public double calculateAverageRating() {
	    return (ratingCount > 0) ? totalRatingScore / ratingCount : 0;
	}
	
}
