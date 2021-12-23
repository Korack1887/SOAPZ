package com.example.SOAPZ.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Ticket {


    protected int price;

    @OneToOne
    protected Session session;

    @Id
    @GeneratedValue
    protected Long id;

    protected int idSeat;

    protected int idColumn;

    protected boolean status;


    public Ticket(int price, Session session, Long id, int idSeat, int idColumn, boolean status) {
        this.price = price;
        this.session = session;
        this.id = id;
        this.idSeat = idSeat;
        this.idColumn = idColumn;
        this.status = status;
    }

    public Ticket() {

    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int value) {
        this.price = value;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session value) {
        this.session = value;
    }

    public Long getId() {
        return id;
    }

    public int getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(int idSeat) {
        this.idSeat = idSeat;
    }

    public int getIdColumn() {
        return idColumn;
    }

    public void setIdColumn(int idColumn) {
        this.idColumn = idColumn;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean ticketStatus) {
        this.status = ticketStatus;
    }

    public void setId(Long value) {
        this.id = value;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "price=" + price +
                ", session=" + session +
                ", id=" + id +
                ", idSeat=" + idSeat +
                ", idColumn=" + idColumn +
                ", status=" + status +
                '}';
    }
}
