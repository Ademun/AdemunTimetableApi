package org.ademun.timetableapi.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    return groupService.findById(id).map(groupMapper::toDto).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/")
  public ResponseEntity<?> createGroup(@RequestBody GroupDto group) {
    System.out.println(group.toString());
    try {
      GroupDto groupDto = groupMapper.toDto(groupService.save(groupMapper.fromDto(group)));
      return ResponseEntity.ok(groupDto);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/")
  public ResponseEntity<GroupDto> updateGroup(@RequestBody GroupDto group) {
    try {
      GroupDto groupDto = groupMapper.toDto(groupService.save(groupMapper.fromDto(group)));
      return ResponseEntity.ok(groupDto);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
    try {
      groupService.deleteById(id);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @RequestMapping("/{id}/disciplines/")
  public ResponseEntity<List<DisciplineDto>> getDisciplines(@PathVariable Long id) {
    try {
      List<DisciplineDto> disciplineDtoList = groupService.getDisciplines(id).stream()
          .map(disciplineMapper::toDto).toList();
      return ResponseEntity.ok(disciplineDtoList);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @RequestMapping("/{id}/professors/")
  public ResponseEntity<List<ProfessorDto>> getProfessors(@PathVariable Long id) {
    try {
      List<ProfessorDto> professorDtoList = groupService.getProfessors(id).stream()
          .map(professorMapper::toDto).toList();
      return ResponseEntity.ok(professorDtoList);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/{id}/disciplines/")
  public ResponseEntity<?> addDiscipline(@PathVariable Long id,
      @RequestBody DisciplineDto disciplineDto) {
    Discipline discipline = disciplineMapper.fromDto(disciplineDto);
    try {
      groupService.addDiscipline(id, discipline);
      return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/{id}/professors/")
  public ResponseEntity<?> addProfessor(@PathVariable Long id,
      @RequestBody ProfessorDto professorDto) {
    Professor professor = professorMapper.fromDto(professorDto);
    try {
      groupService.addProfessor(id, professor);
      return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("/{groupId}/disciplines/{disciplineId}")
  public ResponseEntity<?> removeDiscipline(@PathVariable Long groupId,
      @PathVariable Long disciplineId) {
    try {
      groupService.removeDiscipline(groupId, disciplineId);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("/{groupId}/professors/{professorId}")
  public ResponseEntity<?> removeProfessor(@PathVariable Long groupId,
      @PathVariable Long professorId) {
    try {
      groupService.removeProfessor(groupId, professorId);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
