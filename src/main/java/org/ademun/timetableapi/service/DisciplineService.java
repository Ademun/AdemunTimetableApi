package org.ademun.timetableapi.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.ademun.timetableapi.entity.Discipline;
import org.ademun.timetableapi.entity.Group;
import org.ademun.timetableapi.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisciplineService {

  private final DisciplineRepository disciplineRepository;

  @Autowired
  public DisciplineService(DisciplineRepository disciplineRepository) {
    this.disciplineRepository = disciplineRepository;
  }

  @Transactional
  public Discipline save(Discipline discipline) throws IllegalArgumentException {
    if (checkIfExists(discipline)) {
      throw new IllegalArgumentException(
          "Discipline with name " + discipline.getName() + " already exists");
    }
    return disciplineRepository.save(discipline);
  }

  @Transactional
  public Discipline update(Discipline discipline) throws IllegalArgumentException {
    if (!checkIfExists(discipline)) {
      throw new IllegalArgumentException(
          "Discipline with name " + discipline.getName() + " does not exist"
      );
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
  public Optional<Discipline> findByName(String name) {
    return disciplineRepository.findDisciplineByName(name);
  }

  @Transactional
  public void deleteById(Long id) throws IllegalArgumentException {
    if (!getGroups(id).isEmpty()) {
      throw new IllegalArgumentException(
          "Discipline cannot be deleted because there are groups that are using it");
    }
    disciplineRepository.deleteById(id);
  }

  @Transactional
  public boolean checkIfExists(Discipline discipline) {
    return disciplineRepository.findDisciplineByName(discipline.getName()).isPresent();
  }

  @Transactional
  public Set<Group> getGroups(Long id) throws IllegalArgumentException {
    return disciplineRepository.findById(id).map(Discipline::getGroups).orElseThrow(
        () -> new IllegalArgumentException("Discipline with id " + id + " not found"));
  }
}
