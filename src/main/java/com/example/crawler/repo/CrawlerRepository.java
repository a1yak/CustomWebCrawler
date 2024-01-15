package com.example.crawler.repo;

import com.example.crawler.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CrawlerRepository extends JpaRepository<Site, Long> {
}
