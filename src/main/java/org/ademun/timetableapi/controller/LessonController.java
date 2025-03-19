package org.ademun.timetableapi.controller;

import org.ademun.timetableapi.mapper.LessonMapper;
import org.ademun.timetableapi.model.Lesson;
import org.ademun.timetableapi.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("timetable/lessons")
public class LessonController {
    private final LessonService lessonService;
    private final LessonMapper lessonAssembler;

    @Autowired
    public LessonController(LessonService lessonService, LessonMapper lessonAssembler) {
        this.lessonService = lessonService;
        this.lessonAssembler = lessonAssembler;
    }

    @GetMapping(value = "/")
    public CollectionModel<EntityModel<Lesson>> all() {
        List<EntityModel<Lesson>> lessonModelList = lessonService.all().stream().map(lessonAssembler::toModel).toList();
        return CollectionModel.of(lessonModelList, linkTo(methodOn(LessonController.class).all()).withSelfRel());
    }

    @GetMapping(value = "/{id}")
    public EntityModel<Lesson> one(@PathVariable Long id) {
        return lessonAssembler.toModel(lessonService.one(id));
    }

    @GetMapping(value = "/{week}")
    public CollectionModel<EntityModel<Lesson>> byWeek(@PathVariable int week) {
        List<EntityModel<Lesson>> lessonModelList = lessonService.byWeek(week).stream().map(lessonAssembler::toModel).toList();
        return CollectionModel.of(lessonModelList, linkTo(methodOn(LessonController.class).all()).withSelfRel());
    }

    @GetMapping(value = "/{week}/{day}")
    public CollectionModel<EntityModel<Lesson>> byWeekDay(@PathVariable int week, @PathVariable int day) {
        List<EntityModel<Lesson>> lessonModelList = lessonService.byWeekDay(week, day).stream().map(lessonAssembler::toModel).toList();
        return CollectionModel.of(lessonModelList, linkTo(methodOn(LessonController.class).all()).withSelfRel());
    }

    @GetMapping(value = "/{week}/{day}/{order}")
    public CollectionModel<EntityModel<Lesson>> byWeekDayOrder(@PathVariable int week, @PathVariable int day, @PathVariable int order) {
        List<EntityModel<Lesson>> lessonModelList = lessonService.byWeekDayOrder(week, day, order).stream().map(lessonAssembler::toModel).toList();
        return CollectionModel.of(lessonModelList, linkTo(methodOn(LessonController.class).all()).withSelfRel());
    }

    @PostMapping(value = "/")
    public EntityModel<Lesson> create(@RequestBody Lesson lesson) {
        return lessonAssembler.toModel(lessonService.create(lesson));
    }

    @PutMapping(value = "/{id}")
    public EntityModel<Lesson> update(@PathVariable Long id, @RequestBody Lesson lesson) {
        return lessonAssembler.toModel(lessonService.update(id, lesson));
    }

    @DeleteMapping(value = "/{id}")
    public EntityModel<Lesson> delete(@PathVariable("id") Long id) {
        return lessonAssembler.toModel(lessonService.delete(id));
    }
}
