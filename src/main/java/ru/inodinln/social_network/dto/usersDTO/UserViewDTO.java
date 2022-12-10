package ru.inodinln.social_network.dto.usersDTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserViewDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate regDate;

    private Long countOfSubscribers;

    private LocalDate birthDate;

}
