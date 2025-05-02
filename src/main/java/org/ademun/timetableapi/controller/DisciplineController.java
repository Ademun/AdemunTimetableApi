package org.ademun.timetableapi.controller;

import java.util.List;
import org.ademun.timetableapi.dto.DisciplineDto;
import org.ademun.timetableapi.dto.GroupDto;
import org.ademun.timetableapi.mapper.DisciplineMapper;
import org.ademun.timetableapi.mapper.GroupMapper;
import org.ademun.timetableapi.service.DisciplineService;
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
@RequestMapping("/api/disciplines")
public class DisciplineController {

  private final DisciplineService disciplineService;
  private final DisciplineMapper disciplineMapper;
  private final GroupMapper groupMapper;

  @Autowired
  public DisciplineController(DisciplineService disciplineService,
      DisciplineMapper disciplineMapper, GroupMapper groupMapper) {
    this.disciplineService = disciplineService;
    this.disciplineMapper = disciplineMapper;
    this.groupMapper = groupMapper;
  }

  @RequestMapping("/")
  public ResponseEntity<List<DisciplineDto>> getDisciplines() {
    List<DisciplineDto> disciplineDto =
        disciplineService.findAll().stream().map(disciplineMapper::toDto).toList();
    return ResponseEntity.ok(disciplineDto);
  }

  @RequestMapping("/{id}")
  public ResponseEntity<DisciplineDto> getDiscipline(@PathVariable Long id) {
    return disciplineService.findById(id).map(disciplineMapper::toDto)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/")
  public ResponseEntity<DisciplineDto> createDiscipline(@RequestBody DisciplineDto discipline) {
    try {
      DisciplineDto disciplineDto = disciplineMapper.toDto(
          disciplineService.save(disciplineMapper.fromDto(discipline)));
      return ResponseEntity.ok(disciplineDto);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @PutMapping("/")
  public ResponseEntity<DisciplineDto> updateDiscipline(@RequestBody DisciplineDto discipline) {
    try {
      DisciplineDto disciplineDto = disciplineMapper.toDto(
          disciplineService.update(disciplineMapper.fromDto(discipline)));
      return ResponseEntity.ok(disciplineDto);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteDiscipline(@PathVariable Long id) {
    try {
      disciplineService.deleteById(id);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @RequestMapping("/{id}/groups/")
  public ResponseEntity<List<GroupDto>> getGroups(@PathVariable Long id) {
    try {
      List<GroupDto> groupDtoList =
          disciplineService.getGroups(id).stream().map(groupMapper::toDto).toList();
      return ResponseEntity.ok(groupDtoList);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }
}
