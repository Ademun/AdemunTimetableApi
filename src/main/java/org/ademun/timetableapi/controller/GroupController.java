package org.ademun.timetableapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.ademun.timetableapi.dto.request.DisciplineRequest;
import org.ademun.timetableapi.dto.request.GroupRequest;
import org.ademun.timetableapi.dto.request.ProfessorRequest;
import org.ademun.timetableapi.dto.response.DisciplineResponse;
import org.ademun.timetableapi.dto.response.GroupResponse;
import org.ademun.timetableapi.dto.response.ProfessorResponse;
import org.ademun.timetableapi.service.GroupService;
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
@RequestMapping("/api/groups/")
@Tag(name = "Группы", description = "Управление группами")
public class GroupController {

  private final GroupService groupService;

  @Autowired
  public GroupController(GroupService groupService) {
    this.groupService = groupService;
  }

  @PostMapping
  public ResponseEntity<?> createGroup(@RequestBody @Valid GroupRequest group) {
    return ResponseEntity.ok(groupService.save(group));
  }

  @PostMapping("/{id}/disciplines/")
  public ResponseEntity<?> addDiscipline(@PathVariable Long id,
      @RequestBody @Valid DisciplineRequest discipline) {
    groupService.addDiscipline(id, discipline);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{id}/professors/")
  public ResponseEntity<?> addProfessor(@PathVariable Long id,
      @RequestBody @Valid ProfessorRequest professor) {
    groupService.addProfessor(id, professor);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<List<GroupResponse>> getGroups() {
    return ResponseEntity.ok(groupService.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<GroupResponse> getGroup(@PathVariable Long id) {
    return ResponseEntity.ok(groupService.findById(id));
  }

  @GetMapping(params = {"name"})
  public ResponseEntity<GroupResponse> getGroupByChannelId(@RequestParam String name) {
    return ResponseEntity.ok(groupService.findByName(name));
  }

  @GetMapping(params = {"channelId"})
  public ResponseEntity<GroupResponse> getGroupByChannelId(@RequestParam Long channelId) {
    return ResponseEntity.ok(groupService.findByChannelId(channelId));
  }

  @GetMapping("{id}/disciplines/")
  public ResponseEntity<List<DisciplineResponse>> getDisciplines(@PathVariable Long id) {
    return ResponseEntity.ok(groupService.findDisciplines(id).stream().toList());
  }

  @GetMapping("{id}/professors/")
  public ResponseEntity<List<ProfessorResponse>> getProfessors(@PathVariable Long id) {
    return ResponseEntity.ok(groupService.findProfessors(id).stream().toList());
  }

  @PutMapping("{id}")
  public ResponseEntity<GroupResponse> updateGroup(@PathVariable Long id,
      @RequestBody @Valid GroupRequest group) {
    return ResponseEntity.ok(groupService.update(id, group));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
    groupService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{groupId}/disciplines/{disciplineId}")
  public ResponseEntity<?> removeDiscipline(@PathVariable Long groupId,
      @PathVariable Long disciplineId) {
    groupService.removeDiscipline(groupId, disciplineId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{groupId}/professors/{professorId}")
  public ResponseEntity<?> removeProfessor(@PathVariable Long groupId,
      @PathVariable Long professorId) {
    groupService.removeProfessor(groupId, professorId);
    return ResponseEntity.noContent().build();
  }
}
