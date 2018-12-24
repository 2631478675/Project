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
    private String name;

    @Column
    private String introduction;

    /**
     * According to the number of people, whether the Ticket is still sold
     */
    @Column
    private Integer sumOfPerson;

    @Column
    private Integer remainingTicket;

    @Column
    private  Integer   studentNum;

    @Column
    private Integer childNum;

    @Column
    private Integer adultNum;
    @Column
    private Integer price;
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

    public Integer getRemainingTicket() {
        return remainingTicket;
    }

    public void setRemainingTicket(Integer remainingTicket) {
        this.remainingTicket = remainingTicket;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public Integer getChildNum() {
        return childNum;
    }

    public void setChildNum(Integer childNum) {
        this.childNum = childNum;
    }

    public Integer getAdultNum() {
        return adultNum;
    }

    public void setAdultNum(Integer adultNum) {
        this.adultNum = adultNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Attraction{" +
                            "id=" + id +
                            ", name='" + name + '\'' +
                            ", introduction='" + introduction + '\'' +
                            ", sumOfPerson=" + sumOfPerson +
                            ", remainingTicket=" + remainingTicket +
                            ", studentNum=" + studentNum +
                            ", childNum=" + childNum +
                            ", adultNum=" + adultNum +
                            ", ticket=" + ticket +
                            '}';
    }
}
