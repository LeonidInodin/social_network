package ru.inodinln.social_network.dto.usersDTO;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UserCreatingDTO {

    @NotBlank(message = "Field \"firstName\" is required")
    @Size(min = 2, max = 25, message = "Field \"firstName\" must contains from 2 to 25 letters")
    private String firstName;

    @NotBlank (message = "Field \"lastName\" is required")
    @Size(min = 2, max = 25, message = "Field \"lastName\" must contains from 2 to 25 letters")
    private String lastName;

    @NotBlank(message = "Field \"email\" is required")
    @Email(message = "Field \"email\" invalid format")
    private String email;

    @NotBlank(message = "Field \"password\" is required")
    private String password;

    @NotNull(message = "Field \"birthDate\" is required")
    private LocalDate birthDate;

}
