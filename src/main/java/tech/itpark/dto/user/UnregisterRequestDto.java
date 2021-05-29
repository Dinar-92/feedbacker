package tech.itpark.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import lombok.Value;



@Value
public class UnregisterRequestDto {
  String password;
}
