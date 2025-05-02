package org.ademun.timetableapi.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ademun.timetableapi.dto.request.GroupRequest;
import org.ademun.timetableapi.dto.response.GroupResponse;
import org.ademun.timetableapi.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {

  private final ObjectMapper mapper;

  @Autowired
  public GroupMapper(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public Group fromRequest(GroupRequest request) {
    return mapper.convertValue(request, Group.class);
  }

  public GroupResponse toResponse(Group group) {
    return mapper.convertValue(group, GroupResponse.class);
  }
}
