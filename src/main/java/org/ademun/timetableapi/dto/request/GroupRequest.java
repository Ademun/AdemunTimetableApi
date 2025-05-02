package org.ademun.timetableapi.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupRequest {

  @NotBlank(message = "Name cannot be empty")
  @Size(min = 1, max = 50, message = "Name should be between 1 and 50 characters")
  private String name;
  @NotNull
  @Positive
  private Long channelId;
}
