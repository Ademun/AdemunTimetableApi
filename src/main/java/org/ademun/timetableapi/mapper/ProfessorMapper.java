package org.ademun.timetableapi.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ademun.timetableapi.dto.request.ProfessorRequest;
import org.ademun.timetableapi.dto.response.ProfessorResponse;
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

  public Professor fromRequest(ProfessorRequest request) {
    return mapper.convertValue(request, Professor.class);
  }

  public ProfessorResponse toResponse(Professor professor) {
    return mapper.convertValue(professor, ProfessorResponse.class);
  }
}
