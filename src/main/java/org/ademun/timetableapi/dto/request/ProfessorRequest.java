package org.ademun.timetableapi.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfessorRequest {

  @NotBlank(message = "First name should not be empty")
  @Size(min = 1, max = 50, message = "First name should be between 1 and 50 characters")
  private String firstName;
  @NotBlank(message = "Last name should not be empty")
  @Size(min = 1, max = 50, message = "Last name should be between 1 and 50 characters")
  private String lastName;
  @NotBlank(message = "Patronymic should not be empty")
  @Size(min = 1, max = 50, message = "Patronymic should be between 1 and 50 characters")
  private String patronymic;
  @URL(message = "URL should be a valid address")
  private String url;
}
