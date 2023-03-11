package ru.inodinln.social_network.security;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.usersDTO.UserCreatingDTO;
import ru.inodinln.social_network.dto.usersDTO.UserViewDTO;
import ru.inodinln.social_network.facades.UserFacade;

import javax.validation.Valid;

@RestController
@RequestMapping("/API/v1/auth")
public class AuthenticationController {

    private final UserFacade userFacade;

    public AuthenticationController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping("/register")
    public ResponseEntity<UserViewDTO> create(
            @RequestBody @Valid UserCreatingDTO newUserDTO,
            Authentication authentication) {
        userFacade.create(newUserDTO, authentication);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate
            (@RequestBody @Valid AuthenticationRequestDTO authRequestDTO,
             Authentication authentication) {
        return new ResponseEntity<>(userFacade.authenticate(authRequestDTO, authentication), HttpStatus.OK);
    }



}
