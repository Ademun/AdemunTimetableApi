package org.ademun.timetableapi.controller;

import org.ademun.timetableapi.dto.DisciplineDto;
import org.ademun.timetableapi.mapper.DisciplineMapper;
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

  @Autowired
  public DisciplineController(DisciplineService disciplineService,
      DisciplineMapper disciplineMapper) {
    this.disciplineService = disciplineService;
    this.disciplineMapper = disciplineMapper;
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
  public ResponseEntity<DisciplineDto> createDiscipline(@RequestBody DisciplineDto discipline) {
    DisciplineDto disciplineDto =
        disciplineMapper.toDto(disciplineService.save(disciplineMapper.fromDto(discipline)));
    return ResponseEntity.ok(disciplineDto);
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
}
