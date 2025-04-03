package org.ademun.timetableapi.service;

import jakarta.transaction.Transactional;
import org.ademun.timetableapi.entity.Discipline;
import org.ademun.timetableapi.entity.Group;
import org.ademun.timetableapi.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DisciplineService {
  private final DisciplineRepository disciplineRepository;

  @Autowired
  public DisciplineService(DisciplineRepository disciplineRepository) {
    this.disciplineRepository = disciplineRepository;
  }

  @Transactional
  public Discipline save(Discipline discipline) throws IllegalArgumentException {
    if (!disciplineRepository.findByDisciplineName(discipline.getName()).isEmpty()) {
      throw new IllegalArgumentException("Discipline with this name already exists");
    }
    return disciplineRepository.save(discipline);
  }

  @Transactional
  public List<Discipline> findAll() {
    return disciplineRepository.findAll();
  }

  @Transactional
  public Optional<Discipline> findById(Long id) {
    return disciplineRepository.findById(id);
  }

  @Transactional
  public void deleteById(Long id) {
    if (!getGroups(id).isEmpty()) {
      throw new IllegalArgumentException("Cant delete discipline which is used by groups");
    }
    disciplineRepository.deleteById(id);
  }

  @Transactional
  public Set<Group> getGroups(Long id) {
    Discipline discipline = disciplineRepository.findById(id).orElse(null);
    if (discipline == null) {
      return Collections.emptySet();
    }
    return discipline.getGroups();
  }
}
