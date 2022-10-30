package ru.inodinln.social_network.dto;

import java.time.LocalDate;

public class UserViewDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate regDate;

    private Long countOfSubscribers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public Long getCountOfSubscribers() {
        return countOfSubscribers;
    }

    public void setCountOfSubscribers(Long countOfSubscribers) {
        this.countOfSubscribers = countOfSubscribers;
    }
}
