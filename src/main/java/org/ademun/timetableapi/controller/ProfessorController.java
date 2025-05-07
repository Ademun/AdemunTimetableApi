package org.ademun.timetableapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.ademun.timetableapi.dto.request.ProfessorRequest;
import org.ademun.timetableapi.dto.response.GroupResponse;
import org.ademun.timetableapi.dto.response.ProfessorResponse;
import org.ademun.timetableapi.service.ProfessorService;
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
@RequestMapping("/api/professors/")
@Tag(name = "Преподаватели", description = "Управление преподавателями")
public class ProfessorController {

  private final ProfessorService professorService;

  @Autowired
  public ProfessorController(ProfessorService professorService) {
    this.professorService = professorService;
  }

  @PostMapping
  public ResponseEntity<ProfessorResponse> createProfessor(
      @RequestBody @Valid ProfessorRequest professor) {
    return ResponseEntity.ok(professorService.save(professor));
  }

  @GetMapping
  public ResponseEntity<List<ProfessorResponse>> getProfessors() {
    return ResponseEntity.ok(professorService.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<ProfessorResponse> getProfessorById(@PathVariable Long id) {
    return ResponseEntity.ok(professorService.findById(id));
  }

  @GetMapping(params = {"name"})
  public ResponseEntity<ProfessorResponse> getProfessorByName(@RequestParam String name) {
    return ResponseEntity.ok(professorService.findByFullName(name));
  }

  @GetMapping("{id}/groups/")
  public ResponseEntity<List<GroupResponse>> getGroups(@PathVariable Long id) {
    return ResponseEntity.ok(professorService.findGroups(id).stream().toList());
  }

  @PutMapping("{id}")
  public ResponseEntity<ProfessorResponse> updateProfessor(
      @PathVariable Long id,
      @RequestBody @Valid ProfessorRequest professor) {
    return ResponseEntity.ok(professorService.update(id, professor));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteProfessor(@PathVariable Long id) {
    professorService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
