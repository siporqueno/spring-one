package com.porejemplo.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.porejemplo.persist.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

// DTO
public class UserRepr {

    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @JsonIgnore
    @NotEmpty
    private String matchingPassword;

    @Email
    private String email;

    private Integer age;

    public UserRepr() {
    }

    public UserRepr(String username) {
        this.username = username;
    }

    public UserRepr(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.age = user.getAge();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
