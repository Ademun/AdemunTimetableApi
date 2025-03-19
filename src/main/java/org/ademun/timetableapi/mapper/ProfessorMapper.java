package org.ademun.timetableapi.mapper;

import org.ademun.timetableapi.controller.ProfessorController;
import org.ademun.timetableapi.model.Professor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProfessorMapper implements RepresentationModelAssembler<Professor, EntityModel<Professor>> {
    @NonNull
    @Override
    public EntityModel<Professor> toModel(@NonNull Professor professor) {
        return EntityModel.of(professor,
                linkTo(methodOn(ProfessorController.class).one(professor.getId())).withSelfRel(),
                linkTo(methodOn(ProfessorController.class).all()).withRel("professors"));
    }
}
