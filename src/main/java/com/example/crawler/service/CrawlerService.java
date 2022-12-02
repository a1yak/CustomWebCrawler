package com.example.crawler.service;

import com.example.crawler.model.Site;
import com.example.crawler.repo.CrawlerRepository;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.*;
import java.util.*;

@Service
public class CrawlerService {

    @Autowired
    private CrawlerRepository crawlerRepository;

    private final File csv = new File("sites.csv");
    private final File top = new File("top10.csv");

    private ArrayList<Site> visited = new ArrayList<>();
    private ArrayList<Site> top10 = new ArrayList<>();

    public List<Site> crawl( String url, int level){

        if(level<=8){
            Document document = request(url);

            if(document!=null){

                for(Element link : document.select("a[href]")){

                         String n_link = link.absUrl("href");
                    if(visited.contains(n_link)==false && visited.size()<=10000){

                        crawl(n_link, ++level);

                    }
                }
            }
        }
        return visited;
    }


    private void showStats(Site site){

        System.out.println("Dota occurrences: "+ site.getDotaCount());
        System.out.println("Valorant occurrences: "+ site.getValorantCount());
        System.out.println("PUBG occurrences: "+ site.getPubgCount());
        System.out.println("Counter-Strike occurrences: "+ site.getCsCount());

    }

    private void writeToDb(Site site){

        crawlerRepository.save(site);

    }

    private Document request(String url){
        try{
            Site site = new Site();
            site.setURL(url);
            Connection con = Jsoup.connect(url);
            Document document = con.get();

            for (Element word: document.getElementsMatchingOwnText("Dota"))
            {
                site.setDotaCount(site.getDotaCount()+1);
            }
            for (Element word: document.getElementsMatchingOwnText("Valorant"))
            {
                site.setValorantCount(site.getValorantCount()+1);
            }
            for (Element word: document.getElementsMatchingOwnText("PUBG"))
            {
                site.setPubgCount(site.getPubgCount()+1);
            }
            for (Element word: document.getElementsMatchingOwnText("Counter-Strike"))
            {
                site.setCsCount(site.getCsCount()+1);
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

    private void writeToFile(List<Site> list, File file) throws IOException {
        PrintWriter writer = new PrintWriter(file);
        for(Site s : list){
            writer.println(s);
        }
        writer.close();
    }

    public List<Site> top10Hits()  {
        Collections.sort(visited, (o1, o2) -> ((o2.getCsCount()+ o2.getDotaCount()+ o2.getPubgCount()+ o2.getValorantCount())-(o1.getValorantCount()+o1.getPubgCount()+o1.getDotaCount()+ o1.getCsCount())));

       for(int count = 0; count<10; count++){
           top10.add(visited.get(count));
       }
       try {
           writeToFile(top10, top);
       }catch (IOException exception){
       }

       return top10;
    }

}
