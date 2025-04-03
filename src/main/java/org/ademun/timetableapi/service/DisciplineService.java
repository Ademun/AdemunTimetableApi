package org.ademun.timetableapi.service;

import jakarta.transaction.Transactional;
import org.ademun.timetableapi.entity.Discipline;
import org.ademun.timetableapi.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplineService {
  private final DisciplineRepository disciplineRepository;

  @Autowired
  public DisciplineService(DisciplineRepository disciplineRepository) {
    this.disciplineRepository = disciplineRepository;
  }

  @Transactional
  public Discipline save(Discipline discipline) {
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
    disciplineRepository.deleteById(id);
  }
}
