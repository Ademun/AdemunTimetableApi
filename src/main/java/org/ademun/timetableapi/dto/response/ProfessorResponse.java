package org.ademun.timetableapi.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfessorResponse {

  private Long professorId;
  private String firstName;
  private String lastName;
  private String patronymic;
  private String url;
}
