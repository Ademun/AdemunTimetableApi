package org.ademun.timetableapi.controller;

import org.ademun.timetableapi.mapper.DayMapper;
import org.ademun.timetableapi.model.Day;
import org.ademun.timetableapi.service.DayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/timetable/days")
public class DayController {
    private final DayService dayService;
    private final DayMapper dayMapper;

    @Autowired
    public DayController(DayService dayService, DayMapper dayMapper) {
        this.dayService = dayService;
        this.dayMapper = dayMapper;
    }

    @GetMapping(value = "/")
    public CollectionModel<EntityModel<Day>> all() {
        List<EntityModel<Day>> dayModelList = dayService.all().stream().map(dayMapper::toModel).toList();
        return CollectionModel.of(dayModelList, linkTo(methodOn(DayController.class).all()).withSelfRel());
    }

    @GetMapping(value = "/{id}")
    public EntityModel<Day> one(@PathVariable Long id) {
        return dayMapper.toModel(dayService.one(id));
    }

    @PostMapping(value = "/")
    public EntityModel<Day> create(@RequestBody Day day) {
        return dayMapper.toModel(dayService.create(day));
    }

    @PutMapping(value = "/{id}")
    public EntityModel<Day> update(@PathVariable Long id, @RequestBody Day day) {
        return dayMapper.toModel(dayService.update(id, day));
    }

    @DeleteMapping(value = "/{id}")
    public EntityModel<Day> delete(@PathVariable("id") Long id) {
        return dayMapper.toModel(dayService.delete(id));
    }
}
