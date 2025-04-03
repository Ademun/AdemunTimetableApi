package org.ademun.timetableapi.controller;

import org.ademun.timetableapi.dto.ProfessorDto;
import org.ademun.timetableapi.mapper.ProfessorMapper;
import org.ademun.timetableapi.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professors")
public class ProfessorController {
  private final ProfessorService professorService;
  private final ProfessorMapper professorMapper;

  @Autowired
  public ProfessorController(ProfessorService professorService, ProfessorMapper professorMapper) {
    this.professorService = professorService;
    this.professorMapper = professorMapper;
  }

  @RequestMapping("/")
  public ResponseEntity<List<ProfessorDto>> getProfessors() {
    List<ProfessorDto> professorDto =
        professorService.findAll().stream().map(professorMapper::toDto).toList();
    return ResponseEntity.ok(professorDto);
  }

  @RequestMapping("/{id}")
  public ResponseEntity<ProfessorDto> getProfessor(@PathVariable Long id) {
    ProfessorDto professorDto = professorMapper.toDto(professorService.findById(id).orElseThrow());
    return ResponseEntity.ok(professorDto);
  }

  @PostMapping("/")
  public ResponseEntity<ProfessorDto> createProfessor(@RequestBody ProfessorDto professor) {
    ProfessorDto professorDto =
        professorMapper.toDto(professorService.save(professorMapper.fromDto(professor)));
    return ResponseEntity.ok(professorDto);
  }

  @PutMapping("/")
  public ResponseEntity<ProfessorDto> updateProfessor(@RequestBody ProfessorDto professor) {
    ProfessorDto professorDto =
        professorMapper.toDto(professorService.save(professorMapper.fromDto(professor)));
    return ResponseEntity.ok(professorDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteProfessor(@PathVariable Long id) {
    professorService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
