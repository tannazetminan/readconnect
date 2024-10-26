package com.example.readConnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.readConnect.service.APIBookService;

@CrossOrigin(origins = "http://localhost:8081") 
@RestController
@RequestMapping("/api/books")
public class APIBookController {

    @Autowired
    private APIBookService bookService;
  
    @GetMapping("/search")
    public String searchBooks(@RequestParam String query, 
                              @RequestParam(required = false) String author,
                              @RequestParam(required = false) String category,
                              @RequestParam(required = false) String publisher) {
        return bookService.searchBooksAdvance(query, author, category, publisher);
    }

}
