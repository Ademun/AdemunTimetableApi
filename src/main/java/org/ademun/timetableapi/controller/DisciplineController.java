package org.ademun.timetableapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.ademun.timetableapi.dto.request.DisciplineRequest;
import org.ademun.timetableapi.dto.response.DisciplineResponse;
import org.ademun.timetableapi.dto.response.GroupResponse;
import org.ademun.timetableapi.service.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/disciplines/")
@Tag(name = "Дисциплины", description = "Управление дисциплинами")
public class DisciplineController {

  private final DisciplineService disciplineService;

  @Autowired
  public DisciplineController(DisciplineService disciplineService) {
    this.disciplineService = disciplineService;
  }

  @PostMapping
  public ResponseEntity<DisciplineResponse> createDiscipline(
      @RequestBody @Valid DisciplineRequest discipline) {
    return ResponseEntity.ok(disciplineService.save(discipline));
  }

  @GetMapping
  public ResponseEntity<List<DisciplineResponse>> getDisciplines() {
    return ResponseEntity.ok(disciplineService.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<DisciplineResponse> getDisciplineById(@PathVariable Long id) {
    return ResponseEntity.ok(disciplineService.findById(id));
  }

  @GetMapping(params = {"name"})
  public ResponseEntity<DisciplineResponse> getDisciplineByName(@RequestParam String name) {
    return ResponseEntity.ok(disciplineService.findByName(name));
  }

  @GetMapping("{id}/groups/")
  public ResponseEntity<List<GroupResponse>> getGroups(@PathVariable Long id) {
    return ResponseEntity.ok(disciplineService.findGroups(id).stream().toList());
  }

  @PutMapping("{id}")
  public ResponseEntity<DisciplineResponse> updateDiscipline(
      @PathVariable Long id,
      @RequestBody @Valid DisciplineRequest discipline) {
    return ResponseEntity.ok(disciplineService.update(id, discipline));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteDiscipline(@PathVariable Long id) {
    disciplineService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
