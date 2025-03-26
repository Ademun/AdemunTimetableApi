package org.ademun.timetableapi.mapper;

import org.ademun.timetableapi.controller.DayController;
import org.ademun.timetableapi.model.Day;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DayMapper implements RepresentationModelAssembler<Day, EntityModel<Day>> {
  @NonNull
  @Override
  public EntityModel<Day> toModel(@NonNull Day day) {
    return EntityModel.of(day, linkTo(methodOn(DayController.class).one(day.getId())).withSelfRel(),
        linkTo(methodOn(DayController.class).all()).withRel("days"));
  }
}
