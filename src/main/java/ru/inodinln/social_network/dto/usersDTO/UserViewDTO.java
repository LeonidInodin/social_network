package ru.inodinln.social_network.dto.usersDTO;

import lombok.Data;
import ru.inodinln.social_network.entities.User;
import ru.inodinln.social_network.utils.interfaces.Convertable;

import java.time.LocalDate;

@Data
public class UserViewDTO implements Convertable<User> {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate regDate;

    private Long countOfSubscribers;

    private LocalDate birthDate;

}
