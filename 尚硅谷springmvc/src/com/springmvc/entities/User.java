package com.springmvc.entities;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String age;
    private String adderss;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdderss() {
        return adderss;
    }

    public String getUsername() {
        return username;
    }

    public void setAdderss(String adderss) {
        this.adderss = adderss;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }


    public void to() {
        System.out.println(super.toString());
    }
    public User(int id,String username,String password,String age,String email){
        this.email=email;
        this.age=age;
        this.password=password;
        this.username=username;
        this.id=id;
    }
}
