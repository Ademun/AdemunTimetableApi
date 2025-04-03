package org.ademun.timetableapi.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ademun.timetableapi.dto.DisciplineDto;
import org.ademun.timetableapi.entity.Discipline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DisciplineMapper {
  private final ObjectMapper mapper;

  @Autowired
  public DisciplineMapper(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public Discipline fromDto(DisciplineDto dto) {
    return mapper.convertValue(dto, Discipline.class);
  }

  public DisciplineDto toDto(Discipline discipline) {
    return mapper.convertValue(discipline, DisciplineDto.class);
  }
}
