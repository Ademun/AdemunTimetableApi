package org.ademun.timetableapi.service;

import exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.ademun.timetableapi.dto.request.DisciplineRequest;
import org.ademun.timetableapi.dto.request.GroupRequest;
import org.ademun.timetableapi.dto.request.ProfessorRequest;
import org.ademun.timetableapi.dto.response.DisciplineResponse;
import org.ademun.timetableapi.dto.response.GroupResponse;
import org.ademun.timetableapi.dto.response.ProfessorResponse;
import org.ademun.timetableapi.entity.Discipline;
import org.ademun.timetableapi.entity.Group;
import org.ademun.timetableapi.entity.Professor;
import org.ademun.timetableapi.mapper.DisciplineMapper;
import org.ademun.timetableapi.mapper.GroupMapper;
import org.ademun.timetableapi.mapper.ProfessorMapper;
import org.ademun.timetableapi.repository.DisciplineRepository;
import org.ademun.timetableapi.repository.GroupRepository;
import org.ademun.timetableapi.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GroupService {

  private final GroupRepository repository;
  private final DisciplineRepository disciplineRepository;
  private final ProfessorRepository professorRepository;
  private final GroupMapper mapper;
  private final DisciplineMapper disciplineMapper;
  private final ProfessorMapper professorMapper;

  @Autowired
  public GroupService(GroupRepository repository, DisciplineRepository disciplineRepository,
      ProfessorRepository professorRepository, GroupMapper mapper,
      DisciplineMapper disciplineMapper, ProfessorMapper professorMapper) {
    this.repository = repository;
    this.disciplineRepository = disciplineRepository;
    this.professorRepository = professorRepository;
    this.mapper = mapper;
    this.disciplineMapper = disciplineMapper;
    this.professorMapper = professorMapper;
  }

  public GroupResponse save(GroupRequest request) throws IllegalArgumentException {
    Group group = mapper.fromRequest(request);
    if (checkIfExists(group)) {
      throw new IllegalArgumentException("Group already exists");
    }
    return mapper.toResponse(repository.save(group));
  }

  public List<GroupResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  public GroupResponse findById(Long id) {
    return mapper.toResponse(repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Group not found")));
  }

  public GroupResponse findByName(String name) {
    return mapper.toResponse(repository.findByGroupName(name)
        .orElseThrow(() -> new ResourceNotFoundException("Group not found")));
  }

  public GroupResponse findByChannelId(Long channelId) {
    return mapper.toResponse(repository.findByChannelId(channelId)
        .orElseThrow(() -> new ResourceNotFoundException("Group not found")));
  }

  public Set<DisciplineResponse> findDisciplines(Long id) {
    Set<Discipline> disciplines = repository.findById(id).map(Group::getDisciplines)
        .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
    return disciplines.stream().map(disciplineMapper::toResponse).collect(Collectors.toSet());
  }

  public Set<ProfessorResponse> findProfessors(Long id) {
    Set<Professor> professors = repository.findById(id).map(Group::getProfessors)
        .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
    return professors.stream().map(professorMapper::toResponse).collect(Collectors.toSet());
  }

  public GroupResponse update(Long id, GroupRequest request) throws IllegalArgumentException {
    Group existing = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
    Group group = mapper.fromRequest(request);
    existing.setName(group.getName());
    existing.setChannelId(group.getChannelId());
    return mapper.toResponse(repository.save(existing));
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  public void addDiscipline(Long id, DisciplineRequest request) {
    Group group = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
    Discipline discipline = disciplineRepository.findDisciplineByName(request.getName())
        .orElseThrow(() -> new ResourceNotFoundException("Discipline not found"));
    group.getDisciplines().add(discipline);
    discipline.getGroups().add(group);
    repository.save(group);
  }

  public void addProfessor(Long id, ProfessorRequest request) {
    Group group = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
    Professor professor = professorRepository.findByProfessorFullName(request.getFirstName(),
            request.getLastName(), request.getPatronymic())
        .orElseThrow(() -> new ResourceNotFoundException("Professor not found"));
    group.getProfessors().add(professor);
    professor.getGroups().add(group);
    repository.save(group);
  }

  public void removeDiscipline(Long groupId, Long disciplineId) {
    Group group = repository.findById(groupId)
        .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
    Discipline discipline = disciplineRepository.findById(disciplineId)
        .orElseThrow(() -> new ResourceNotFoundException("Discipline not found"));
    group.getDisciplines().remove(discipline);
    discipline.getGroups().remove(group);
    repository.save(group);
  }

  public void removeProfessor(Long groupId, Long professorId) {
    Group group = repository.findById(groupId)
        .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
    Professor professor = professorRepository.findById(professorId)
        .orElseThrow(() -> new ResourceNotFoundException("Professor not found"));
    group.getProfessors().remove(professor);
    professor.getGroups().remove(group);
    repository.save(group);
  }

  private boolean checkIfExists(Group group) {
    return repository.findByGroupName(group.getName()).isPresent();
  }
}
