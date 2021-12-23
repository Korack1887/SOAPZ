package com.example.SOAPZ.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Film {


    protected String title;

    protected String director;

    protected String duration;

    protected String description;

    protected String language;


    @Id
    @GeneratedValue
    protected Long id;
    @Enumerated(EnumType.STRING)
    protected Genre genre;

    public Film() {

    }

    public Film(Long id, String title, String director, String duration, String description12, Genre genre, String language) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.duration = duration;
        this.description = description12;
        this.genre = genre;
        this.language = language;

    }

    public Film(String title, String director, String duration, String description12, Genre genre, String language) {
        this.title = title;
        this.director = director;
        this.duration = duration;
        this.description = description12;
        this.genre = genre;
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id &&
                Objects.equals(title, film.title) &&
                Objects.equals(director, film.director) &&
                Objects.equals(duration, film.duration) &&
                Objects.equals(description, film.description) &&
                Objects.equals(language, film.language) &&
                genre == film.genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, director, duration, description, language, id, genre);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long idFilm) {
        this.id = idFilm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", duration='" + duration + '\'' +
                ", description='" + description + '\'' +
                ", language='" + language + '\'' +
                ", id=" + id +
                ", genre=" + genre +
                '}';
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String value) {
        this.duration = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre value) {
        this.genre = value;
    }

    ;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String value) {
        this.language = value;
    }
}
