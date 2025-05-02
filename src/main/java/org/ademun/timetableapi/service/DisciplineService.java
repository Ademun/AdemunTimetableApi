package org.ademun.timetableapi.service;

import exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.ademun.timetableapi.dto.request.DisciplineRequest;
import org.ademun.timetableapi.dto.response.DisciplineResponse;
import org.ademun.timetableapi.dto.response.GroupResponse;
import org.ademun.timetableapi.entity.Discipline;
import org.ademun.timetableapi.entity.Group;
import org.ademun.timetableapi.mapper.DisciplineMapper;
import org.ademun.timetableapi.mapper.GroupMapper;
import org.ademun.timetableapi.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DisciplineService {

  private final DisciplineRepository repository;
  private final DisciplineMapper mapper;
  private final GroupMapper groupMapper;

  @Autowired
  public DisciplineService(DisciplineRepository repository,
      DisciplineMapper mapper, GroupMapper groupMapper) {
    this.repository = repository;
    this.mapper = mapper;
    this.groupMapper = groupMapper;
  }

  public DisciplineResponse save(DisciplineRequest request)
      throws IllegalArgumentException {
    Discipline discipline = mapper.fromRequest(request);
    if (checkIfExists(discipline)) {
      throw new IllegalArgumentException(
          "Discipline already exists");
    }
    return mapper.toResponse(repository.save(discipline));
  }

  public List<DisciplineResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  public DisciplineResponse findById(Long id) {
    return mapper.toResponse(repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Discipline not found")));
  }

  public DisciplineResponse findByName(String name) {
    return mapper.toResponse(repository.findDisciplineByName(name)
        .orElseThrow(() -> new ResourceNotFoundException("Discipline not found")));
  }

  public Set<GroupResponse> findGroups(Long id) {
    Set<Group> groups = repository.findById(id).map(Discipline::getGroups).orElseThrow(
        () -> new ResourceNotFoundException("Discipline not found"));
    return groups.stream().map(groupMapper::toResponse).collect(Collectors.toSet());
  }

  public DisciplineResponse update(Long id, DisciplineRequest request)
      throws IllegalArgumentException {
    Discipline existing = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Discipline not found"));
    Discipline discipline = mapper.fromRequest(request);
    existing.setName(discipline.getName());
    existing.setUrl(discipline.getUrl());
    return mapper.toResponse(repository.save(existing));
  }

  public void deleteById(Long id) {
    if (!findGroups(id).isEmpty()) {
      throw new IllegalArgumentException(
          "Discipline cannot be deleted because there are groups that are using it");
    }
    repository.deleteById(id);
  }

  private boolean checkIfExists(Discipline discipline) {
    return repository.findDisciplineByName(discipline.getName()).isPresent();
  }
}
