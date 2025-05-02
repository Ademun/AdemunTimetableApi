package org.ademun.timetableapi.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DisciplineRequest {

  @NotBlank(message = "Name cannot be empty")
  @Size(min = 3, max = 70, message = "Name should be between 3 and 70 characters")
  private String name;
  @URL(message = "URL should be a valid address")
  private String url;
}
