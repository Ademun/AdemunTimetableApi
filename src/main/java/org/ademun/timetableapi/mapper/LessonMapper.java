package org.ademun.timetableapi.mapper;

import org.ademun.timetableapi.controller.LessonController;
import org.ademun.timetableapi.model.Lesson;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LessonMapper implements RepresentationModelAssembler<Lesson, EntityModel<Lesson>> {
    @NonNull
    @Override
    public EntityModel<Lesson> toModel(@NonNull Lesson lesson) {
        return EntityModel.of(lesson,
                linkTo(methodOn(LessonController.class).one(lesson.getId())).withSelfRel(),
                linkTo(methodOn(LessonController.class).all()).withRel("lessons"));
    }
}
