package com.example.deki.application.pojo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "attraction")
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attraction_id")
    private Long id;

    @Column
    private String introduction;

    /**
     * According to the number of people, whether the Ticket is still sold
     */
    @Column
    private Integer sumOfPerson;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @ElementCollection(targetClass=Ticket.class)
    @Column(name = "ticket_id")
    private List<Ticket> ticket;

    public Attraction() {

    }

    public Attraction(String introduction, Integer sumOfPerson, List<Ticket> ticket) {
        this.introduction = introduction;
        this.sumOfPerson = sumOfPerson;
        this.ticket = ticket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getSumOfPerson() {
        return sumOfPerson;
    }

    public void setSumOfPerson(Integer sumOfPerson) {
        this.sumOfPerson = sumOfPerson;
    }

    public List<Ticket> getTicket() {
        return ticket;
    }

    public void setTicket(List<Ticket> ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "Attraction{" +
                            "id=" + id +
                            ", introduction='" + introduction + '\'' +
                            ", sumOfPerson=" + sumOfPerson +
                            ", ticket=" + ticket +
                            '}';
    }
}
