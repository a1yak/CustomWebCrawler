package com.example.crawler.service;

import com.example.crawler.model.Site;
import com.example.crawler.repo.CrawlerRepository;
import lombok.Data;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.*;
import java.util.*;

@Service
@Data
public class CrawlerService {

    @Autowired
    private CrawlerRepository crawlerRepository;

    private final File csv = new File("sites.csv");
    private final File top = new File("top10.csv");

    private ArrayList<Site> visited = new ArrayList<>();
    private ArrayList<Site> top10 = new ArrayList<>();

    public List<Site> crawl( String url, String word, int level){

        if(level<=8){
            Document document = request(url, word);

            if(document!=null){

                for(Element link : document.select("a[href]")){

                         String n_link = link.absUrl("href");
                    if(!visited.contains(n_link) && visited.size()<=10000){

                        crawl(n_link, word, ++level);

                    }
                }
            }
        }
        return visited;
    }


    private void showStats(Site site){
        System.out.println(site.getSearchedWord()+" occurrences: "+ site.getSearchedWordCount());
    }

    private void writeToDb(Site site){

        crawlerRepository.save(site);

    }

    public Document request(String url, String word){
        try{
            Site site = new Site();
            site.setUrl(url);
            site.setSearchedWord(word);
            Connection con = Jsoup.connect(url);
            Document document = con.get();

            for (Element searchedWord: document.getElementsMatchingOwnText(word))
            {
                site.setSearchedWordCount(site.getSearchedWordCount()+1);
            }

            System.out.println("Link: " + url);
            System.out.println(document.title());
            showStats(site);
            writeToDb(site);
            writeToFile(visited, csv);
            visited.add(site);
            return document;


        } catch (IOException exception){
            return null;
        }
    }

    public void writeToFile(List<Site> list, File file) throws IOException {
        PrintWriter writer = new PrintWriter(file);
        for(Site s : list){
            writer.println(s);
        }
        writer.close();
    }

    public List<Site> top10Hits() {
        visited.sort(Comparator.comparingInt(Site::getSearchedWordCount));

       for(int count = 0; count<10; count++){
           top10.add(visited.get(count));
       }
       try {
           writeToFile(top10, top);
       }catch (IOException exception){
           System.out.println(exception.getMessage());
       }

       return top10;
    }

}
