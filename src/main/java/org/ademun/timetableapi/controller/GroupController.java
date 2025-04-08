package org.ademun.timetableapi.controller;

import org.ademun.timetableapi.dto.DisciplineDto;
import org.ademun.timetableapi.dto.GroupDto;
import org.ademun.timetableapi.dto.ProfessorDto;
import org.ademun.timetableapi.entity.Discipline;
import org.ademun.timetableapi.entity.Professor;
import org.ademun.timetableapi.mapper.DisciplineMapper;
import org.ademun.timetableapi.mapper.GroupMapper;
import org.ademun.timetableapi.mapper.ProfessorMapper;
import org.ademun.timetableapi.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
  private final GroupService groupService;
  private final GroupMapper groupMapper;
  private final DisciplineMapper disciplineMapper;
  private final ProfessorMapper professorMapper;

  @Autowired
  public GroupController(GroupService groupService, GroupMapper groupMapper,
      DisciplineMapper disciplineMapper, ProfessorMapper professorMapper) {
    this.groupService = groupService;
    this.groupMapper = groupMapper;
    this.disciplineMapper = disciplineMapper;
    this.professorMapper = professorMapper;
  }

  @RequestMapping("/")
  public ResponseEntity<List<GroupDto>> getGroups() {
    List<GroupDto> groupDto = groupService.findAll().stream().map(groupMapper::toDto).toList();
    return ResponseEntity.ok(groupDto);
  }

  @RequestMapping("/{id}")
  public ResponseEntity<GroupDto> getGroup(@PathVariable Long id) {
    GroupDto groupDto = groupMapper.toDto(groupService.findById(id).orElseThrow());
    return ResponseEntity.ok(groupDto);
  }

  @RequestMapping("/{id}/disciplines/")
  public ResponseEntity<List<DisciplineDto>> getDisciplines(@PathVariable Long id) {
    List<DisciplineDto> disciplineDtoList =
        groupService.getDisciplines(id).stream().map(disciplineMapper::toDto).toList();
    return ResponseEntity.ok(disciplineDtoList);
  }

  @RequestMapping("/{id}/professors/")
  public ResponseEntity<List<ProfessorDto>> getProfessors(@PathVariable Long id) {
    List<ProfessorDto> professorDtoList =
        groupService.getProfessors(id).stream().map(professorMapper::toDto).toList();
    return ResponseEntity.ok(professorDtoList);
  }

  @PostMapping("/")
  public ResponseEntity<?> createGroup(@RequestBody GroupDto group) {
    try {
      GroupDto groupDto =
          groupMapper.toDto(groupService.save(groupMapper.fromDto(group)).orElseThrow());
      return ResponseEntity.ok(groupDto);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body("A group with this name already exists");
    }
  }

  @PostMapping("/{id}/disciplines/")
  public ResponseEntity<?> createDiscipline(@PathVariable Long id,
      @RequestBody DisciplineDto disciplineDto) {
    Discipline discipline = disciplineMapper.fromDto(disciplineDto);
    System.out.println(disciplineDto + " " + discipline);
    groupService.addDiscipline(id, discipline);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{id}/professors/")
  public ResponseEntity<?> createProfessor(@PathVariable Long id,
      @RequestBody ProfessorDto professorDto) {
    Professor professor = professorMapper.fromDto(professorDto);
    groupService.addProfessor(id, professor);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/")
  public ResponseEntity<GroupDto> updateGroup(@RequestBody GroupDto group) {
    GroupDto groupDto =
        groupMapper.toDto(groupService.save(groupMapper.fromDto(group)).orElseThrow());
    return ResponseEntity.ok(groupDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
    groupService.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{group_id}/disciplines/{discipline_id}")
  public ResponseEntity<?> removeDiscipline(@PathVariable Long group_id,
      @PathVariable Long discipline_id) {
    groupService.removeDiscipline(group_id, discipline_id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{group_id}/professors/{professor_id}")
  public ResponseEntity<?> removeProfessor(@PathVariable Long group_id,
      @PathVariable Long professor_id) {
    groupService.removeProfessor(group_id, professor_id);
    return ResponseEntity.ok().build();
  }
}
