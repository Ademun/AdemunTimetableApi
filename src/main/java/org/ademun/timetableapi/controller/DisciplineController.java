package org.ademun.timetableapi.controller;

import org.ademun.timetableapi.dto.DisciplineDto;
import org.ademun.timetableapi.dto.GroupDto;
import org.ademun.timetableapi.mapper.DisciplineMapper;
import org.ademun.timetableapi.mapper.GroupMapper;
import org.ademun.timetableapi.service.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    DisciplineDto disciplineDto =
        disciplineMapper.toDto(disciplineService.findById(id).orElseThrow());
    return ResponseEntity.ok(disciplineDto);
  }

  @PostMapping("/")
  public ResponseEntity<?> createDiscipline(@RequestBody DisciplineDto discipline) {
    try {
      DisciplineDto disciplineDto =
          disciplineMapper.toDto(disciplineService.save(disciplineMapper.fromDto(discipline)));
      return ResponseEntity.ok(disciplineDto);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body("Discipline with this name already exists");
    }
  }

  @PutMapping("/")
  public ResponseEntity<DisciplineDto> updateDiscipline(@RequestBody DisciplineDto discipline) {
    DisciplineDto disciplineDto =
        disciplineMapper.toDto(disciplineService.save(disciplineMapper.fromDto(discipline)));
    return ResponseEntity.ok(disciplineDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteDiscipline(@PathVariable Long id) {
    disciplineService.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @RequestMapping("/{id}/groups/")
  public ResponseEntity<List<GroupDto>> getGroups(@PathVariable Long id) {
    List<GroupDto> groupDtoList =
        disciplineService.getGroups(id).stream().map(groupMapper::toDto).toList();
    return ResponseEntity.ok(groupDtoList);
  }
}
