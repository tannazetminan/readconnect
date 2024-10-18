package com.example.readConnect.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
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
    
    @Lob
    private byte[] image;

    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Intrest> intrests = new HashSet<>();

    
    //constructors
    public User() {}
      
	public User(String username, String password, String email, String country, int age, boolean mode, Set<Intrest> intrests) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.country = country;
		this.age = age;
		this.mode = mode;
		this.intrests = intrests;
	}

	public User(String username, String password, String email, String country, int age, boolean mode, Set<Intrest> intrests, byte[] image) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.country = country;
		this.age = age;
		this.mode = mode;
		this.image = image;
		this.intrests = intrests;
	}
	

	//getters & setters
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
}
