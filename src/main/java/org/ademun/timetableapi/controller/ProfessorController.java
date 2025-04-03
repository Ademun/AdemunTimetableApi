package org.ademun.timetableapi.controller;

import org.ademun.timetableapi.dto.GroupDto;
import org.ademun.timetableapi.dto.ProfessorDto;
import org.ademun.timetableapi.mapper.GroupMapper;
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
  private final GroupMapper groupMapper;

  @Autowired
  public ProfessorController(ProfessorService professorService, ProfessorMapper professorMapper,
      GroupMapper groupMapper) {
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
    ProfessorDto professorDto = professorMapper.toDto(professorService.findById(id).orElseThrow());
    return ResponseEntity.ok(professorDto);
  }

  @PostMapping("/")
  public ResponseEntity<?> createProfessor(@RequestBody ProfessorDto professor) {
    try {
      ProfessorDto professorDto =
          professorMapper.toDto(professorService.save(professorMapper.fromDto(professor)));
      return ResponseEntity.ok(professorDto);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body("Professor with this name already exists");
    }
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

  @RequestMapping("/{id}/groups/")
  public ResponseEntity<List<GroupDto>> getGroups(@PathVariable Long id) {
    List<GroupDto> groupDtoList =
        professorService.getGroups(id).stream().map(groupMapper::toDto).toList();
    return ResponseEntity.ok(groupDtoList);
  }
}
