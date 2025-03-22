package org.ademun.timetableapi.controller;

import org.ademun.timetableapi.mapper.ProfessorMapper;
import org.ademun.timetableapi.model.Professor;
import org.ademun.timetableapi.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/timetable/professors")
public class ProfessorController {
    private final ProfessorService professorService;
    private final ProfessorMapper professorMapper;

    @Autowired
    public ProfessorController(ProfessorService professorService, ProfessorMapper professorMapper) {
        this.professorService = professorService;
        this.professorMapper = professorMapper;
    }

    @GetMapping(value = "/")
    public CollectionModel<EntityModel<Professor>> all() {
        List<EntityModel<Professor>> professorModelList = professorService.all().stream().map(professorMapper::toModel).toList();
        return CollectionModel.of(professorModelList, linkTo(methodOn(ProfessorController.class).all()).withSelfRel());
    }

    @GetMapping(value = "/{id}")
    public EntityModel<Professor> one(@PathVariable Integer id) {
        return professorMapper.toModel(professorService.one(id));
    }

    @PostMapping(value = "/")
    public EntityModel<Professor> create(@RequestBody Professor professor) {
        return professorMapper.toModel(professorService.create(professor));
    }

    @PutMapping(value = "/{id}")
    public EntityModel<Professor> update(@PathVariable Integer id, @RequestBody Professor professor) {
        return professorMapper.toModel(professorService.update(id, professor));
    }

    @DeleteMapping(value = "/{id}")
    public EntityModel<Professor> delete(@PathVariable("id") Integer id) {
        return professorMapper.toModel(professorService.delete(id));
    }
}
