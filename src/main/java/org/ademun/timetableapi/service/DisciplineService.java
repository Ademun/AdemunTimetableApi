package org.ademun.timetableapi.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ademun.timetableapi.entity.Discipline;
import org.ademun.timetableapi.entity.Group;
import org.ademun.timetableapi.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class DisciplineService {
  private final DisciplineRepository disciplineRepository;

  @Autowired
  public DisciplineService(DisciplineRepository disciplineRepository) {
    this.disciplineRepository = disciplineRepository;
  }

  @Transactional
  public Optional<Discipline> save(Discipline discipline) {
    log.trace("Saving discipline {}", discipline.getName());
    if (!disciplineRepository.findByDisciplineName(discipline.getName()).isEmpty()) {
      log.warn("Discipline with name {} already exists", discipline.getName());
      return Optional.empty();
    }
    return Optional.of(disciplineRepository.save(discipline));
  }

  @Transactional
  public List<Discipline> findAll() {
    log.trace("Retrieving all disciplines");
    return disciplineRepository.findAll();
  }

  @Transactional
  public Optional<Discipline> findById(Long id) {
    log.trace("Retrieving discipline with id {}", id);
    return disciplineRepository.findById(id);
  }

  @Transactional
  public void deleteById(Long id) {
    log.trace("Deleting discipline with id {}", id);
    if (!getGroups(id).isEmpty()) {
      log.warn("This discipline is being used by other groups");
      return;
    }
    disciplineRepository.deleteById(id);
  }

  @Transactional
  public Set<Group> getGroups(Long id) {
    Discipline discipline = disciplineRepository.findById(id).orElse(null);
    if (discipline == null) {
      log.warn("Discipline with id {} not found", id);
      return Collections.emptySet();
    }
    return discipline.getGroups();
  }
}
