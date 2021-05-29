package tech.itpark.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
// credentials ->
public class LoginResponseDto {
  private String token;
}
