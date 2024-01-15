package com.example.crawler.controller;

import com.example.crawler.model.Site;
import com.example.crawler.repo.CrawlerRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CrawlerControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CrawlerRepository crawlerRepository;

    private String baseURL = "http://localhost:8080/api/v1/crawler";

    @Test
    public void testStartCrawler() {
        // Arrange
        String url = "https://www.sports.ru";
        String searchedWord = "messi";

        // Act
        URI uri = UriComponentsBuilder.fromHttpUrl(baseURL)
                .queryParam("url", url)
                .queryParam("searchedWord", searchedWord)
                .build()
                .toUri();

        ResponseEntity<List> response = restTemplate.getForEntity(
                uri,
                List.class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testTopHits() {
        // Arrange
        String url = "https://www.sports.ru";
        String searchedWord = "messi";


        // Act
        URI uri = UriComponentsBuilder.fromHttpUrl(baseURL)
                .queryParam("url", url)
                .queryParam("searchedWord", searchedWord)
                .build()
                .toUri();

        ResponseEntity<List> response = restTemplate.getForEntity(
                uri,
                List.class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 5);

    }
}
