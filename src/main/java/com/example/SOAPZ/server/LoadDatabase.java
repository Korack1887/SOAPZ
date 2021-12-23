package com.example.SOAPZ.server;

import com.example.SOAPZ.entity.Film;
import com.example.SOAPZ.entity.Genre;
import com.example.SOAPZ.repository.FilmRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(FilmRepo repository) {

        return args -> {
           // System.out.println("Preloading " + repository.save(new Film("title1", "dir1", "01:01:01", "description1", Genre.COMEDY, "ua")));
           // System.out.println("Preloading " + repository.save(new Film("title2", "dir2", "01:01:01", "description2", Genre.COMEDY, "en")));
        };
    }
}
