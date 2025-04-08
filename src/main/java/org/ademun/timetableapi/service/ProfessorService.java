package org.ademun.timetableapi.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ademun.timetableapi.entity.Group;
import org.ademun.timetableapi.entity.Professor;
import org.ademun.timetableapi.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ProfessorService {
  private final ProfessorRepository professorRepository;

  @Autowired
  public ProfessorService(ProfessorRepository professorRepository) {
    this.professorRepository = professorRepository;
  }

  @Transactional
  public Optional<Professor> save(Professor professor) {
    log.trace("Saving professor {}  {} {}", professor.getFirstName(), professor.getLastName(),
        professor.getPatronymic());
    if (!professorRepository.findByProfessorFullName(professor.getFirstName(),
        professor.getLastName(), professor.getPatronymic()).isEmpty()) {
      log.warn("Professor {} {} {} already exists", professor.getFirstName(),
          professor.getLastName(), professor.getPatronymic());
      return Optional.empty();
    }
    return Optional.of(professorRepository.save(professor));
  }

  @Transactional
  public List<Professor> findAll() {
    log.trace("Retrieving all professors");
    return professorRepository.findAll();
  }

  @Transactional
  public Optional<Professor> findById(Long id) {
    log.trace("Retrieving professor with id {}", id);
    return professorRepository.findById(id);
  }

  @Transactional
  public void deleteById(Long id) {
    log.trace("Deleting professor with id {}", id);
    if (!getGroups(id).isEmpty()) {
      log.warn("This professor is being used by other groups");
      return;
    }
    professorRepository.deleteById(id);
  }

  @Transactional
  public Set<Group> getGroups(Long id) {
    Professor professor = professorRepository.findById(id).orElse(null);
    if (professor == null) {
      log.warn("Professor with id {} not found", id);
      return Collections.emptySet();
    }
    return professor.getGroups();
  }
}
