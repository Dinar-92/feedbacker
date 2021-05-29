package tech.itpark.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
public class RestoreRequestDto {
  String login;
  String password;
  String secret;
}