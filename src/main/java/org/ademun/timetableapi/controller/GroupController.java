package org.ademun.timetableapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ademun.timetableapi.mapper.GroupMapper;
import org.ademun.timetableapi.model.Group;
import org.ademun.timetableapi.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/timetable/groups")
public class GroupController {
  private final GroupService groupService;
  private final GroupMapper groupMapper;
  private final ObjectMapper objectMapper;

  @Autowired
  public GroupController(GroupService groupService, GroupMapper groupMapper,
      ObjectMapper objectMapper) {
    this.groupService = groupService;
    this.groupMapper = groupMapper;
    this.objectMapper = objectMapper;
  }

  @GetMapping(value = "/")
  public CollectionModel<EntityModel<Group>> all() {
    List<EntityModel<Group>> groupModelList =
        groupService.all().stream().map(groupMapper::toModel).toList();
    return CollectionModel.of(groupModelList,
        linkTo(methodOn(GroupController.class).all()).withSelfRel());
  }

  @GetMapping(value = "/{id}")
  public EntityModel<Group> one(@PathVariable Integer id) {
    return groupMapper.toModel(groupService.one(id));
  }

  @PostMapping(value = "/")
  public EntityModel<Group> create(@RequestBody String group) throws JsonProcessingException {
    Group parsedGroup = objectMapper.readValue(group, Group.class);
    System.out.println(parsedGroup.toString());
    return groupMapper.toModel(groupService.create(parsedGroup));
  }

  @PutMapping(value = "/{id}")
  public EntityModel<Group> update(@PathVariable Integer id, @RequestBody Group group) {
    return groupMapper.toModel(groupService.update(id, group));
  }

  @DeleteMapping(value = "/{id}")
  public EntityModel<Group> delete(@PathVariable("id") Integer id) {
    return groupMapper.toModel(groupService.delete(id));
  }
}
