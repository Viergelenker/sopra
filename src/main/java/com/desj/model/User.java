package com.desj.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julien on 14.04.16.
 */

@Entity
@Component
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Size(min=6, max=30)
    private String email;
    private String username;
    @NotNull
    private String password;
    //Studiengang
    private String major;

    @OneToMany
    @JoinTable(
            name = "CREATEDMCQUESTION",
           joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "MCQUESTION_ID")
    )
    List <MCQuestion> createdMCQuestions = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "ANSWEREDMCQUESTIONS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "MCQUESTION_ID")
    )
    List<MCQuestion> answeredMCQuestions = new ArrayList<>();

    @OneToMany
    @JoinTable(
            name = "CREATEDQUESTION",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "QUESTION_ID")
    )
    List <Question> createdQuestions = new ArrayList<>();

    public Integer getId() { return id; }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public List<MCQuestion> getCreatedMCQuestions() {
        return createdMCQuestions;
    }

    public void setCreatedMCQuestions(List<MCQuestion> createdMCQuestions) {
        this.createdMCQuestions = createdMCQuestions;
    }

    public List<MCQuestion> getAnsweredMCQuestions() {
        return answeredMCQuestions;
    }

    public void setAnsweredMCQuestions(List<MCQuestion> answeredMCQuestions) {
        this.answeredMCQuestions = answeredMCQuestions;
    }

    public List<Question> getCreatedQuestions() {
        return createdQuestions;
    }

    public void createMCQuestion(MCQuestion mcQuestion){
        this.createdMCQuestions.add(mcQuestion);
    }

    public void createQuestion(Question question){
        this.createdQuestions.add(question);
    }

    public void answerMCQuestion(MCQuestion mcQuestion){
        this.answeredMCQuestions.add(mcQuestion);
    }

    public void setCreatedQuestions(List<Question> createdQuestions) {
        this.createdQuestions = createdQuestions;
    }
}
