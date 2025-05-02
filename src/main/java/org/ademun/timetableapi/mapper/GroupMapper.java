package org.ademun.timetableapi.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ademun.timetableapi.dto.GroupDto;
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

  public Group fromDto(GroupDto dto) {
    return mapper.convertValue(dto, Group.class);
  }

  public GroupDto toDto(Group group) {
    return mapper.convertValue(group, GroupDto.class);
  }
}
