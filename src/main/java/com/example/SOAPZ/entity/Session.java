
package com.example.SOAPZ.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Session {
    @Id
    @GeneratedValue
    protected Long id;

    protected String date;

    protected String time;

    @OneToOne
    private Hall hall;
    @OneToOne
    private Film film;

    public Session() {

    }

    public Session(Long id, String date, String time, Hall hall, Film film) {
        this.id = id;
        this.film = film;
        this.date = date;
        this.time = time;
        this.hall = hall;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", hall=" + hall +
                ", film=" + film +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long idSession) {
        this.id = idSession;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String value) {
        this.date = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String value) {
        this.time = value;
    }

    public Hall getHall() {
        return hall;
    }

    public Film getFilm() {
        return film;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

}
