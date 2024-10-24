package com.example.readConnect.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.readConnect.model.*;
import com.example.readConnect.service.BookService;

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
            @RequestParam String description) {
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

            
            // Set interests later
            // Assuming intrests is a List<String> (or Set)

            Book createdBook = bookService.createBook(writerId, bookDetails);
            return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
//    @PostMapping("/create")
//    public ResponseEntity<Book> createBook(
//            @RequestParam Long writerId, 
//            @RequestParam("title") String title, 
//            @RequestParam("author") String author, 
//            @RequestParam("isbn") String isbn, 
//            @RequestParam("image") MultipartFile image) {
//        try {
//            Book bookDetails = new Book();
//            bookDetails.setTitle(title);
//            bookDetails.setAuthor(author);
//            bookDetails.setIsbn(isbn);
//
//            // Convert the MultipartFile to byte[]
//            if (image != null && !image.isEmpty()) {
//                bookDetails.setImage(image.getBytes());
//            }
//
//            Book createdBook = bookService.createBook(writerId, bookDetails);
//            return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }

    

    
    
    // create a book
    @PostMapping("/create2")
    public ResponseEntity<Book> createBook(@RequestParam Long writerId, @RequestBody BookCreateDto bookDto) {
        try {
            Book bookDetails = new Book();
            bookDetails.setImage(bookDto.getImage());
            bookDetails.setTitle(bookDto.getTitle());
            bookDetails.setAuthor(bookDto.getAuthor());
            bookDetails.setIsbn(bookDto.getIsbn());
//            bookDetails.setLocation("");
//            bookDetails.setSoldNum(0);

            Book createdBook = bookService.createBook(writerId, bookDetails);
            return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    
    
//  // create a book
//  @PostMapping("/create")
//  public ResponseEntity<Book> createBook(@RequestParam Long clientId, @RequestBody Book bookDetails) {
//      try {
//          Book createdBook = bookService.createBook(clientId,  bookDetails);
//          return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
//      } catch (RuntimeException e) {
//          return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//      }
//  }

  

//    // update a book
//    @PutMapping("/{bookId}")
//    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody BookUpdateDto bookUpdateDto) {
//        try {
//            // Delegate the update operation to a service method
//            Book updatedBook = bookService.updateBook(bookId, bookUpdateDto);
//            return ResponseEntity.ok(updatedBook);
//        } catch (RuntimeException e) {
//            // Handle not found or other exceptions appropriately
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }


    // get book base on intrests
    @GetMapping("/intrests/{intrest}")
    public ResponseEntity<List<Book>> getBookByIntrests(@PathVariable String intrest){
    	
    	try {
    	    List<Book> books = bookRepository.findBooksByIntrestName(intrest);
    	    if (books.isEmpty()) {
    	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	    } else {
    	        return new ResponseEntity<>(books, HttpStatus.OK);
    	    }
    	} catch (Exception e) {
    	    return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
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


 
//  // give a new rating to a book, when it is finished
//  @PostMapping("/{bookId}/rating")
//  public ResponseEntity<Book> rateWorker(@PathVariable Long bookId, @RequestBody Rating rating) {
//      try {
//          Book updatedBook = bookService.rateBook(bookId, rating.getRating());
//          return new ResponseEntity<>(updatedBook, HttpStatus.OK);
//      } catch (RuntimeException e) {
//          return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//      }
//  }
    
    
    // get all books for a specific writer
//    @GetMapping("/user/{writerId}")
//    public ResponseEntity<List<Book>> getBooksByWriterId(@PathVariable Long writerId) {
//        try {
//            List<Book> books = bookRepository.findByWriterId(writerId);
//            if (books.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(books, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
