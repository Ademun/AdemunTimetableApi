package org.ademun.timetableapi.mapper;

import org.ademun.timetableapi.controller.GroupController;
import org.ademun.timetableapi.model.Group;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GroupMapper implements RepresentationModelAssembler<Group, EntityModel<Group>> {
    @NonNull
    @Override
    public EntityModel<Group> toModel(@NonNull Group group) {
        return EntityModel.of(group,
                linkTo(methodOn(GroupController.class).one(group.getId())).withSelfRel(),
                linkTo(methodOn(GroupController.class).all()).withRel("groups"));
    }
}
