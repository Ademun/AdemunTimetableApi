package org.ademun.timetableapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupDto {
  private Long group_id;
  private String name;
  private Long channel_id;
  private Set<DisciplineDto> disciplines = new LinkedHashSet<>();
  private Set<ProfessorDto> professors = new LinkedHashSet<>();
}
