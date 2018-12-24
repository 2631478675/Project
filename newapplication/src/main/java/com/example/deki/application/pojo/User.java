package com.example.deki.application.pojo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    /**
     * 0 : is not super
     * 1: is super
     */
    @Column
    private Integer ifSuper;

    /**
     * judging whether it is a promised person based on age
     */
    @Column
    private Integer age;

    /**
     * 0 : is not student
     * 1: is student

     */
    @Column
    private Integer ifStudent;


    @Column
    private String password;

    @Override
    public String toString() {
        return "User{" +
                            "id=" + id +
                            ", name='" + name + '\'' +
                            ", ifSuper=" + ifSuper +
                            ", age=" + age +
                            ", ifStudent=" + ifStudent +
                            ", password='" + password + '\'' +
                            ", attraction=" + attraction +
                            '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @ElementCollection(targetClass=Attraction.class)
    @Column(name = "attraction_id")
    private List<Attraction> attraction;

    public User() {
    }

    public User(String name, Integer ifSuper, Integer age, Integer ifStudent, List<Attraction> attraction) {
        this.name = name;
        this.ifSuper = ifSuper;
        this.age = age;
        this.ifStudent = ifStudent;
        this.attraction = attraction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIfSuper() {
        return ifSuper;
    }

    public void setIfSuper(Integer ifSuper) {
        this.ifSuper = ifSuper;
    }

    public List<Attraction> getAttraction() {
        return attraction;
    }

    public void setAttraction(List<Attraction> attraction) {
        this.attraction = attraction;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getIfStudent() {
        return ifStudent;
    }

    public void setIfStudent(Integer ifStudent) {
        this.ifStudent = ifStudent;
    }

}
