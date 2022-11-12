package ru.inodinln.social_network.dto.usersDTO;


import lombok.Data;
import ru.inodinln.social_network.entities.User;
import ru.inodinln.social_network.utils.interfaces.Convertable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UserCreationDTO implements Convertable<User> {

    @NotNull
    @NotBlank
    @Size(min = 2, max = 25)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 25)
    private String lastName;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    private LocalDate birthDate;

}
