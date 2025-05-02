package org.ademun.timetableapi.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ademun.timetableapi.dto.ProfessorDto;
import org.ademun.timetableapi.entity.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {

  private final ObjectMapper mapper;

  @Autowired
  public ProfessorMapper(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public Professor fromDto(ProfessorDto dto) {
    return mapper.convertValue(dto, Professor.class);
  }

  public ProfessorDto toDto(Professor professor) {
    return mapper.convertValue(professor, ProfessorDto.class);
  }
}
