package org.ademun.timetableapi.controller;

import org.ademun.timetableapi.mapper.DisciplineMapper;
import org.ademun.timetableapi.model.Discipline;
import org.ademun.timetableapi.service.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/timetable/disciplines")
public class DisciplineController {
    private final DisciplineService disciplineService;
    private final DisciplineMapper disciplineMapper;

    @Autowired
    public DisciplineController(DisciplineService disciplineService, DisciplineMapper disciplineMapper) {
        this.disciplineService = disciplineService;
        this.disciplineMapper = disciplineMapper;
    }

    @GetMapping(value = "/")
    public CollectionModel<EntityModel<Discipline>> all() {
        List<EntityModel<Discipline>> disciplineModelList = disciplineService.all().stream().map(disciplineMapper::toModel).toList();
        return CollectionModel.of(disciplineModelList, linkTo(methodOn(DisciplineController.class).all()).withSelfRel());
    }

    @GetMapping(value = "/{id}")
    public EntityModel<Discipline> one(@PathVariable Integer id) {
        return disciplineMapper.toModel(disciplineService.one(id));
    }

    @PostMapping(value = "/")
    public EntityModel<Discipline> create(@RequestBody Discipline discipline) {
        return disciplineMapper.toModel(disciplineService.create(discipline));
    }

    @PutMapping(value = "/{id}")
    public EntityModel<Discipline> update(@PathVariable Integer id, @RequestBody Discipline discipline) {
        return disciplineMapper.toModel(disciplineService.update(id, discipline));
    }

    @DeleteMapping(value = "/{id}")
    public EntityModel<Discipline> delete(@PathVariable("id") Integer id) {
        return disciplineMapper.toModel(disciplineService.delete(id));
    }
}
