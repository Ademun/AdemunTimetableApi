package org.ademun.timetableapi.controller;

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

    @Autowired
    public GroupController(GroupService groupService, GroupMapper groupMapper) {
        this.groupService = groupService;
        this.groupMapper = groupMapper;
    }

    @GetMapping(value = "/")
    public CollectionModel<EntityModel<Group>> all() {
        List<EntityModel<Group>> groupModelList = groupService.all().stream().map(groupMapper::toModel).toList();
        return CollectionModel.of(groupModelList, linkTo(methodOn(GroupController.class).all()).withSelfRel());
    }

    @GetMapping(value = "/{id}")
    public EntityModel<Group> one(@PathVariable Integer id) {
        return groupMapper.toModel(groupService.one(id));
    }

    @PostMapping(value = "/")
    public EntityModel<Group> create(@RequestBody Group group) {
        return groupMapper.toModel(groupService.create(group));
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
