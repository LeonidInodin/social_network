package ru.inodinln.social_network.security;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthenticationRequestDTO {

  @NotBlank(message = "Field \"eMail\" is required")
  private String email;

  @NotBlank(message = "Field \"password\" is required")
  private String password;
}
