package com.example.crawler.controller;

import com.example.crawler.model.Site;
import com.example.crawler.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crawler")
public class CrawlerController {

    @Autowired
    private CrawlerService crawlerService;

    @GetMapping("/{url}")
    public ResponseEntity<List<Site>> startCrawler(@PathVariable String url){
       return new ResponseEntity<>(crawlerService.crawl("https://"+url, 1), HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity<List<Site>> topHits(){
        return new ResponseEntity<>(crawlerService.top10Hits(), HttpStatus.OK);
    }
}
