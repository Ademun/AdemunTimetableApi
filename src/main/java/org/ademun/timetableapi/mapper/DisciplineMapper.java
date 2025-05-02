package org.ademun.timetableapi.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ademun.timetableapi.dto.request.DisciplineRequest;
import org.ademun.timetableapi.dto.response.DisciplineResponse;
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

  public Discipline fromRequest(DisciplineRequest request) {
    return mapper.convertValue(request, Discipline.class);
  }

  public DisciplineResponse toResponse(Discipline discipline) {
    return mapper.convertValue(discipline, DisciplineResponse.class);
  }
}
