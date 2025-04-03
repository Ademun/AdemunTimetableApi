package org.ademun.timetableapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfessorDto {
  private Long professor_id;
  private String firstName;
  private String lastName;
  private String patronymic;
  private String url;
  private Set<GroupDto> groups = new LinkedHashSet<>();
}
