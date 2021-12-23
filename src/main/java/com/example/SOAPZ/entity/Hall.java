package com.example.SOAPZ.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Hall {

    protected String type;
    protected int columnQuantity;

    protected int seatQuantity;
    @Id
    @GeneratedValue
    protected Long id;

    public Hall() {

    }

    public Hall(Long id, String type, int columnQuantity, int seatQuantity) {
        this.type = type;
        this.columnQuantity = columnQuantity;
        this.seatQuantity = seatQuantity;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Hall{" +
                "type='" + type + '\'' +
                ", columnQuantity=" + columnQuantity +
                ", seatQuantity=" + seatQuantity +
                ", id=" + id +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long idHall) {
        this.id = idHall;
    }

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public int getColumnQuantity() {
        return columnQuantity;
    }

    public void setColumnQuantity(int value) {
        this.columnQuantity = value;
    }

    public int getSeatQuantity() {
        return seatQuantity;
    }

    public void setSeatQuantity(int value) {
        this.seatQuantity = value;
    }
}
