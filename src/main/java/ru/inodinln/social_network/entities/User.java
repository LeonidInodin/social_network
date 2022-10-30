package ru.inodinln.social_network.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private LocalDate regDate;

    private int roleId;

    private long countOfSubscribers;

    //@JsonIgnore
    @OneToMany(mappedBy = "from")
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "from")
    private List<Message> sendedMessages;

    @OneToMany(mappedBy = "to")
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    @PrePersist
    private void prePersist() {
        setRegDate(LocalDate.now());
        setCountOfSubscribers(0);
        setRoleId(1);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public long getCountOfSubscribers() {
        return countOfSubscribers;
    }

    public void setCountOfSubscribers(long countOfSubscribers) {
        this.countOfSubscribers = countOfSubscribers;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<Message> getSendedMessages() {
        return sendedMessages;
    }

    public void setSendedMessages(List<Message> sendedMessages) {
        this.sendedMessages = sendedMessages;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }
}
