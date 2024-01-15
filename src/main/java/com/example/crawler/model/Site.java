package com.example.crawler.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Site")
public class Site  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String searchedWord;
    private Integer searchedWordCount = 0;

}
