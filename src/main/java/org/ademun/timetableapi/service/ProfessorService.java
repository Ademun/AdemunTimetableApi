package org.ademun.timetableapi.service;

import jakarta.transaction.Transactional;
import org.ademun.timetableapi.entity.Group;
import org.ademun.timetableapi.entity.Professor;
import org.ademun.timetableapi.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProfessorService {
  private final ProfessorRepository professorRepository;

  @Autowired
  public ProfessorService(ProfessorRepository professorRepository) {
    this.professorRepository = professorRepository;
  }

  @Transactional
  public Professor save(Professor professor) throws IllegalArgumentException {
    if (!professorRepository.findByProfessorFullName(professor.getFirstName(),
        professor.getLastName(), professor.getPatronymic()).isEmpty()) {
      throw new IllegalArgumentException("Professor with this name already exists");
    }
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
    if (!getGroups(id).isEmpty()) {
      throw new IllegalArgumentException("Cant delete professor which is used by groups");
    }
    professorRepository.deleteById(id);
  }

  @Transactional
  public Set<Group> getGroups(Long id) {
    Professor professor = professorRepository.findById(id).orElse(null);
    if (professor == null) {
      return Collections.emptySet();
    }
    return professor.getGroups();
  }
}
