package com.example.readConnect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String username;
	private String password;
	private String email;
	private String country;
	private int age;
	private boolean mode;
	private String intrests;
	private String phone;

	@Lob
	private byte[] image;

	// constructors
	public User() {
	}

	public User(String username, String password, String email, String country, int age, boolean mode, String intrests,
			byte[] image) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.country = country;
		this.age = age;
		this.mode = mode;
		this.intrests = intrests;
		this.image = image;
	}

	public User(String username, String password, String email, String country, int age, boolean mode,
			String intrests) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.country = country;
		this.age = age;
		this.mode = mode;
		this.intrests = intrests;
	}

	// getters & setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean getMode() {
		return mode;
	}

	public void setMode(boolean mode) {
		this.mode = mode;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getIntrests() {
		return intrests;
	}

	public void setIntrests(String intrests) {
		this.intrests = intrests;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
