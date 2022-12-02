package com.example.crawler.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "Site")
public class Site  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String URL;
    private Integer dotaCount=0;
    private Integer valorantCount=0;
    private Integer csCount=0;
    private Integer pubgCount=0;




}
