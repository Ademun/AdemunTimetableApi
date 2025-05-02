package org.ademun.timetableapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfessorDto {

  private Long professorId;
  private String firstName;
  private String lastName;
  private String patronymic;
  private String url;
}
