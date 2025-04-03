package org.ademun.timetableapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupDto {
  private Long group_id;
  private String name;
  private Long channel_id;
}
