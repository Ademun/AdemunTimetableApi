package org.ademun.timetableapi.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.ademun.timetableapi.entity.Discipline;
import org.ademun.timetableapi.entity.Group;
import org.ademun.timetableapi.entity.Professor;
import org.ademun.timetableapi.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

  private final GroupRepository groupRepository;
  private final DisciplineService disciplineService;
  private final ProfessorService professorService;

  @Autowired
  public GroupService(GroupRepository groupRepository, DisciplineService disciplineService,
      ProfessorService professorService) {
    this.groupRepository = groupRepository;
    this.disciplineService = disciplineService;
    this.professorService = professorService;
  }

  @Transactional
  public Group save(Group group) throws IllegalArgumentException {
    if (checkIfExists(group)) {
      throw new IllegalArgumentException("Group with name " + group.getName() + " already exists");
    }
    return groupRepository.save(group);
  }

  @Transactional
  public List<Group> findAll() {
    return groupRepository.findAll();
  }

  @Transactional
  public Optional<Group> findById(Long id) {
    return groupRepository.findById(id);
  }

  @Transactional
  public void deleteById(Long id) throws IllegalArgumentException {
    Group group = groupRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Group with id " + id + " not found"));
    group.getDisciplines().forEach(discipline -> discipline.getGroups().remove(group));
    group.getProfessors().forEach(professor -> professor.getGroups().remove(group));
    groupRepository.deleteById(id);
  }

  @Transactional
  public Set<Discipline> getDisciplines(Long id) throws IllegalArgumentException {
    Group group = groupRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Group with id " + id + " not found"));
    return group.getDisciplines();
  }

  @Transactional
  public Set<Professor> getProfessors(Long id) throws IllegalArgumentException {
    Group group = groupRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Group with id " + id + " not found"));
    return group.getProfessors();
  }

  @Transactional
  public void addDiscipline(Long id, Discipline discipline) throws IllegalArgumentException {
    Group group = groupRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Group with id " + id + " not found"));
    Discipline entry = disciplineService.findByName(discipline.getName())
        .orElse(disciplineService.save(discipline));
    entry.getGroups().add(group);
  }

  @Transactional
  public void addProfessor(Long id, Professor professor) throws IllegalArgumentException {
    Group group = groupRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Group with id " + id + " not found"));
    Professor entry = professorService.findByFullName(professor.getFullName())
        .orElse(professorService.save(professor));
    entry.getGroups().add(group);
  }

  @Transactional
  public void removeDiscipline(Long groupId, Long disciplineId) throws IllegalArgumentException {
    Group group = groupRepository.findById(groupId)
        .orElseThrow(() -> new IllegalArgumentException("Group with id " + groupId + " not found"));
    Discipline discipline = disciplineService.findById(disciplineId).orElseThrow(
        () -> new IllegalArgumentException("Discipline with id " + disciplineId + " not found"));
    discipline.getGroups().remove(group);
  }

  @Transactional
  public void removeProfessor(Long groupId, Long professorId) throws IllegalArgumentException {
    Group group = groupRepository.findById(groupId)
        .orElseThrow(() -> new IllegalArgumentException("Group with id " + groupId + " not found"));
    Professor professor = professorService.findById(professorId).orElseThrow(
        () -> new IllegalArgumentException("Professor with id " + professorId + " not found"));
    professor.getGroups().remove(group);
  }

  @Transactional
  public boolean checkIfExists(Group group) {
    return groupRepository.findByGroupName(group.getName()).isPresent();
  }
}
