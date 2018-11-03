package com.example.deki.application.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @Column
    private double price;

    @Column
    private Integer total;

    /**
     * Judging whether it is the peak season according to the time of ticketing
     */
    @Column
    private Date timeTicket;

    public Ticket() {

    }

    public Ticket(double price, Integer total, Date timeTicket) {
        this.price = price;
        this.total = total;
        this.timeTicket = timeTicket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Date getTimeTicket() {
        return timeTicket;
    }

    public void setTimeTicket(Date timeTicket) {
        this.timeTicket = timeTicket;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                            "id=" + id +
                            ", price=" + price +
                            ", total=" + total +
                            ", timeTicke=" + timeTicket +
                            '}';
    }
}

