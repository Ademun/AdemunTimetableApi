package org.ademun.timetableapi.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.ademun.timetableapi.entity.Group;
import org.ademun.timetableapi.entity.Professor;
import org.ademun.timetableapi.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

  private final ProfessorRepository professorRepository;

  @Autowired
  public ProfessorService(ProfessorRepository professorRepository) {
    this.professorRepository = professorRepository;
  }

  @Transactional
  public Professor save(Professor professor) throws IllegalArgumentException {
    if (checkIfExists(professor)) {
      throw new IllegalArgumentException(
          "Professor with name " + professor.getFullName() + " already exists");
    }
    return professorRepository.save(professor);
  }

  @Transactional
  public Professor update(Professor professor) throws IllegalArgumentException {
    if (!checkIfExists(professor)) {
      throw new IllegalArgumentException(
          "Professor with name " + professor.getFullName() + " does not exist"
      );
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
  public Optional<Professor> findByFullName(String fullName) {
    String[] splitName = fullName.split(" ");
    return professorRepository.findByProfessorFullName(splitName[0], splitName[1], splitName[2]);
  }

  @Transactional
  public void deleteById(Long id) throws IllegalArgumentException {
    if (!getGroups(id).isEmpty()) {
      throw new IllegalArgumentException(
          "Professor cannot be deleted because there are groups that are using it");
    }
    professorRepository.deleteById(id);
  }

  @Transactional
  public boolean checkIfExists(Professor professor) {
    return professorRepository.findByProfessorFullName(professor.getFirstName(),
        professor.getLastName(), professor.getPatronymic()).isPresent();
  }

  @Transactional
  public Set<Group> getGroups(Long id) throws IllegalArgumentException {
    return professorRepository.findById(id).map(Professor::getGroups).orElseThrow(
        () -> new IllegalArgumentException("Professor with id" + id + " not found"));
  }
}
