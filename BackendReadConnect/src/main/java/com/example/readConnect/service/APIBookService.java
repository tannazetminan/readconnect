package com.example.readConnect.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class APIBookService {

    private final String API_URL = "https://www.googleapis.com/books/v1/volumes";

    public String searchBooks(String query) {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("q", query)
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }
    
    public String searchBooksAdvance(String query, String author, String category, String publisher) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("q", query);

        if (author != null && !author.isEmpty()) {
            builder.queryParam("inauthor", author);
        }
        if (category != null && !category.isEmpty()) {
            builder.queryParam("subject", category);
        }
        if (publisher != null && !publisher.isEmpty()) {
            builder.queryParam("inpublisher", publisher);
        }

        String url = builder.toUriString();
        return restTemplate.getForObject(url, String.class);
    }

}
