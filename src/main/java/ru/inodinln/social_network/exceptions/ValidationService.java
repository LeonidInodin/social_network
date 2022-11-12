package ru.inodinln.social_network.exceptions;

import ru.inodinln.social_network.dto.usersDTO.UserCreationDTO;
import ru.inodinln.social_network.dto.usersDTO.UserUpdateDTO;
import ru.inodinln.social_network.exceptions.validationException.ValidationException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

public class ValidationService {

    private ValidationService(){}

    public static void UserCreationDtoValidation(UserCreationDTO userDto){
        firstNameValidation(userDto.getFirstName());
        lastNameValidation(userDto.getLastName());
        birthDateValidation(userDto.getBirthDate());

        String concat = userDto.getFirstName()+userDto.getLastName();
        if (!((concat.matches("[A-z]*$")) || (concat.matches("[А-я]*$"))))
            throw new ValidationException("Fields \"firstName\" and \"lastName\" must contains symbols from the same language");
    }

    public static void UserUpdateDtoValidation(UserUpdateDTO userDto){
        firstNameValidation(userDto.getFirstName());
        lastNameValidation(userDto.getLastName());
        birthDateValidation(userDto.getBirthDate());

        String concat = userDto.getFirstName()+userDto.getLastName();
        if (!((concat.matches("[A-z]*$")) || (concat.matches("[А-я]*$"))))
            throw new ValidationException("Fields \"firstName\" and \"lastName\" must contains symbols from the same language");
    }

    public static void birthDateValidation(LocalDate birthDate) {
        if (birthDate == null)
            throw new ValidationException("Birth date field can't be empty");

        if (birthDate.isAfter(LocalDate.now()))
            throw new ValidationException("Birth date can't be in the future");

        long age = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        if (age < 14 || age > 100)
            throw new ValidationException("Age must be between 14 and 100");

    }

    public static void firstNameValidation(String firstName){
        if (firstName.contains(" "))
            throw new ValidationException("field \"firstName\" must contains the only one word");
        if (!firstName.matches("[A-zА-я]*$"))
            throw new ValidationException("field \"firstName\" must contains a letters only");
    }

    public static void lastNameValidation(String lastName){
        if (lastName.contains(" "))
            throw new ValidationException("field \"lastName\" must contains the only one word");
        if (!Pattern.matches("[A-zА-я]*$", lastName))
            throw new ValidationException("field \"lastName\" must contains a letters only");
    }

}
