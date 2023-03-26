package com.example.crawler.controller;

import com.example.crawler.model.Site;
import com.example.crawler.repo.CrawlerRepository;
import com.example.crawler.service.CrawlerService;
import jakarta.servlet.ServletContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
@WebMvcTest(CrawlerController.class)
class CrawlerControllerTest {
    @InjectMocks
    private CrawlerController crawlerController;
    @Mock
    private CrawlerService crawlerService;

    @Test
    public void testStartCrawlerWithValidParameters() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        String url = "https://example.com";
        String searchedWord = "example";
        List<Site> expectedSites = new ArrayList<>();
        Site site1 = new Site();
        site1.setURL("https://example.com/page1");
        site1.setSearchedWord("example");
        Site site2 = new Site();
        site2.setURL("https://example.com/page1");
        site2.setSearchedWord("ex");
        expectedSites.add(site1);
        expectedSites.add(site2);

        CrawlerController controller = new CrawlerController();

        when(crawlerService.crawl(url, searchedWord, 1)).thenReturn(expectedSites);
        // Act
        ResponseEntity<List<Site>> response = controller.startCrawler(url, searchedWord);
        List<Site> sites = response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(sites);
        assertTrue(sites.size() > 0);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedSites, sites);
    }
}