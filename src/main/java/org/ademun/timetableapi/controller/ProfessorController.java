package org.ademun.timetableapi.controller;

import java.util.List;
import org.ademun.timetableapi.dto.GroupDto;
import org.ademun.timetableapi.dto.ProfessorDto;
import org.ademun.timetableapi.mapper.GroupMapper;
import org.ademun.timetableapi.mapper.ProfessorMapper;
import org.ademun.timetableapi.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/professors")
public class ProfessorController {

  private final ProfessorService professorService;
  private final ProfessorMapper professorMapper;
  private final GroupMapper groupMapper;

  @Autowired
  public ProfessorController(ProfessorService professorService,
      ProfessorMapper professorMapper, GroupMapper groupMapper) {
    this.professorService = professorService;
    this.professorMapper = professorMapper;
    this.groupMapper = groupMapper;
  }

  @RequestMapping("/")
  public ResponseEntity<List<ProfessorDto>> getProfessors() {
    List<ProfessorDto> professorDto =
        professorService.findAll().stream().map(professorMapper::toDto).toList();
    return ResponseEntity.ok(professorDto);
  }

  @RequestMapping("/{id}")
  public ResponseEntity<ProfessorDto> getProfessor(@PathVariable Long id) {
    return professorService.findById(id).map(professorMapper::toDto)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/")
  public ResponseEntity<ProfessorDto> createProfessor(@RequestBody ProfessorDto professor) {
    try {
      ProfessorDto professorDto = professorMapper.toDto(
          professorService.save(professorMapper.fromDto(professor)));
      return ResponseEntity.ok(professorDto);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @PutMapping("/")
  public ResponseEntity<ProfessorDto> updateProfessor(@RequestBody ProfessorDto professor) {
    try {
      ProfessorDto professorDto = professorMapper.toDto(
          professorService.update(professorMapper.fromDto(professor)));
      return ResponseEntity.ok(professorDto);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteProfessor(@PathVariable Long id) {
    try {
      professorService.deleteById(id);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @RequestMapping("/{id}/groups/")
  public ResponseEntity<List<GroupDto>> getGroups(@PathVariable Long id) {
    try {
      List<GroupDto> groupDtoList =
          professorService.getGroups(id).stream().map(groupMapper::toDto).toList();
      return ResponseEntity.ok(groupDtoList);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }
}
