package org.ademun.timetableapi.service;

import jakarta.transaction.Transactional;
import org.ademun.timetableapi.entity.Professor;
import org.ademun.timetableapi.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
  private final ProfessorRepository professorRepository;

  @Autowired
  public ProfessorService(ProfessorRepository professorRepository) {
    this.professorRepository = professorRepository;
  }

  @Transactional
  public Professor save(Professor professor) {
    return professorRepository.save(professor);
  }

  @Transactional
  public List<Professor> findAll() {
    return professorRepository.findAll();
  }

  @Transactional
  public Optional<Professor> findById(Long id) {
    return professorRepository.findById(id);
  }

  @Transactional
  public void deleteById(Long id) {
    professorRepository.deleteById(id);
  }
}
