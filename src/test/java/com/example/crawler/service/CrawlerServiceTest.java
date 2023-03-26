package com.example.crawler.service;

import com.example.crawler.model.Site;
import com.example.crawler.repo.CrawlerRepository;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CrawlerServiceTest {

    @Mock
    private CrawlerRepository crawlerRepository;

    @InjectMocks
    private CrawlerService crawlerService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void crawl_shouldReturnVisitedSites() {
        // Arrange
        String url = "https://example.com";
        String word = "example";
        int level = 1;
        List<Site> expectedVisited = new ArrayList<>();
        Site site1 = new Site();
        site1.setURL(url);
        site1.setSearchedWord(word);
        site1.setSearchedWordCount(1);
        expectedVisited.add(site1);
        when(crawlerRepository.save(any(Site.class))).thenReturn(site1);

        // Act
        List<Site> actualVisited = crawlerService.crawl(url, word, level);

        // Assert
        verify(crawlerRepository, times(1)).save(site1);
    }

    @Test
    public void top10Hits_shouldReturnTop10Sites(){
        // Arrange
        ArrayList<Site> visited = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Site site = new Site();
            site.setURL("https://example.com/" + i);
            site.setSearchedWord("example");
            site.setSearchedWordCount(i);
            visited.add(site);
        }
        crawlerService.setVisited(visited);
        // Assert
     assertDoesNotThrow(
             ()-> {
                 crawlerService.top10Hits();
             }
     );
    }

}