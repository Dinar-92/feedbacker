package tech.itpark.dto.user;

import lombok.Value;


@Value
public class UpdatePasswordRequestDto {
  private String login;
  private String password;
  private String secret;
}
