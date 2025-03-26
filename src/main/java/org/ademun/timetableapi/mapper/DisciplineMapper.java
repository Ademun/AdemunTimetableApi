package org.ademun.timetableapi.mapper;

import org.ademun.timetableapi.controller.DisciplineController;
import org.ademun.timetableapi.model.Discipline;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DisciplineMapper
    implements RepresentationModelAssembler<Discipline, EntityModel<Discipline>> {
  @NonNull
  @Override
  public EntityModel<Discipline> toModel(@NonNull Discipline discipline) {
    return EntityModel.of(discipline,
        linkTo(methodOn(DisciplineController.class).one(discipline.getId())).withSelfRel(),
        linkTo(methodOn(DisciplineController.class).all()).withRel("disciplines"));
  }
}
