package com.example.crawler.controller;

import com.example.crawler.model.Site;
import com.example.crawler.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crawler")
public class CrawlerController {

    @Autowired
    private CrawlerService crawlerService;

    @GetMapping
    public ResponseEntity<List<Site>> startCrawler(@RequestParam String url, @RequestParam String searchedWord){
       return new ResponseEntity<>(crawlerService.crawl(url, searchedWord, 1), HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity<List<Site>> topHits(){
        return new ResponseEntity<>(crawlerService.top10Hits(), HttpStatus.OK);
    }
}
