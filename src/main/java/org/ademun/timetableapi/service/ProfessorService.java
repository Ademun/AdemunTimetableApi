package org.ademun.timetableapi.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.ademun.timetableapi.dto.request.ProfessorRequest;
import org.ademun.timetableapi.dto.response.GroupResponse;
import org.ademun.timetableapi.dto.response.ProfessorResponse;
import org.ademun.timetableapi.entity.Group;
import org.ademun.timetableapi.entity.Professor;
import org.ademun.timetableapi.exception.ResourceNotFoundException;
import org.ademun.timetableapi.mapper.GroupMapper;
import org.ademun.timetableapi.mapper.ProfessorMapper;
import org.ademun.timetableapi.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProfessorService {

  private final ProfessorRepository repository;
  private final ProfessorMapper mapper;
  private final GroupMapper groupMapper;

  @Autowired
  public ProfessorService(ProfessorRepository repository,
      ProfessorMapper mapper, GroupMapper groupMapper) {
    this.repository = repository;
    this.mapper = mapper;
    this.groupMapper = groupMapper;
  }

  public ProfessorResponse save(ProfessorRequest request)
      throws IllegalArgumentException {
    Professor professor = mapper.fromRequest(request);
    if (checkIfExists(professor)) {
      throw new IllegalArgumentException(
          "Professor already exists");
    }
    return mapper.toResponse(repository.save(professor));
  }

  public List<ProfessorResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  public ProfessorResponse findById(Long id) {
    return mapper.toResponse(repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Professor not found")));
  }

  public ProfessorResponse findByFullName(String name) {
    String[] split = name.split(" ");
    return mapper.toResponse(repository.findByProfessorFullName(split[0], split[1], split[2])
        .orElseThrow(() -> new ResourceNotFoundException("Professor not found")));
  }

  public Set<GroupResponse> findGroups(Long id) {
    Set<Group> groups = repository.findById(id).map(Professor::getGroups).orElseThrow(
        () -> new ResourceNotFoundException("Professor not found"));
    return groups.stream().map(groupMapper::toResponse).collect(Collectors.toSet());
  }

  public ProfessorResponse update(Long id, ProfessorRequest request)
      throws IllegalArgumentException {
    Professor existing = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Professor not found"));
    Professor professor = mapper.fromRequest(request);
    existing.setFirstName(professor.getFirstName());
    existing.setLastName(professor.getLastName());
    existing.setPatronymic(professor.getPatronymic());
    existing.setUrl(professor.getUrl());
    return mapper.toResponse(repository.save(existing));
  }

  public void deleteById(Long id) {
    if (!findGroups(id).isEmpty()) {
      throw new IllegalArgumentException(
          "Professor cannot be deleted because there are groups that are using it");
    }
    repository.deleteById(id);
  }

  private boolean checkIfExists(Professor professor) {
    return repository.findByProfessorFullName(professor.getFirstName(), professor.getLastName(),
        professor.getPatronymic()).isPresent();
  }
}
