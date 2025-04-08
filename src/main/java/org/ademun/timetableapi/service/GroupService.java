package org.ademun.timetableapi.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ademun.timetableapi.entity.Discipline;
import org.ademun.timetableapi.entity.Group;
import org.ademun.timetableapi.entity.Professor;
import org.ademun.timetableapi.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class GroupService {
  private final GroupRepository groupRepository;
  private final EntityManager entityManager;

  @Autowired
  public GroupService(GroupRepository groupRepository, EntityManager entityManager) {
    this.groupRepository = groupRepository;
    this.entityManager = entityManager;
  }

  @Transactional
  public Optional<Group> save(Group group) {
    log.trace("Saving group {}", group.getName());
    if (!groupRepository.findByGroupName(group.getName()).isEmpty()) {
      log.warn("Group with this name already exists");
      return Optional.empty();
    }
    return Optional.of(groupRepository.save(group));
  }

  @Transactional
  public List<Group> findAll() {
    log.trace("Retrieving all groups");
    return groupRepository.findAll();
  }

  @Transactional
  public Optional<Group> findById(Long id) {
    log.trace("Retrieving group with id {}", id);
    return groupRepository.findById(id);
  }

  @Transactional
  public void deleteById(Long id) {
    log.trace("Deleting group with id {}", id);
    Group group = groupRepository.findById(id).orElse(null);
    if (group == null) {
      log.warn("Group with id {} not found", id);
      return;
    }
    group.getDisciplines().forEach(discipline -> discipline.getGroups().remove(group));
    group.getProfessors().forEach(professor -> professor.getGroups().remove(group));
    groupRepository.deleteById(id);
  }

  @Transactional
  public Set<Discipline> getDisciplines(Long id) {
    log.trace("Retrieving disciplines of group with id {}", id);
    Group group = groupRepository.findById(id).orElse(null);
    if (group == null) {
      log.warn("Group with id {} not found", id);
      return Collections.emptySet();
    }
    return group.getDisciplines();
  }

  @Transactional
  public Set<Professor> getProfessors(Long id) {
    log.trace("Retrieving professors of group with id {}", id);
    Group group = groupRepository.findById(id).orElse(null);
    if (group == null) {
      log.warn("Group with id {} not found", id);
      return Collections.emptySet();
    }
    return group.getProfessors();
  }

  @Transactional
  public void addDiscipline(Long id, Discipline discipline) {
    log.trace("Adding discipline {} to group with id {}", discipline.getName(), id);
    Group group = groupRepository.findById(id).orElse(null);
    if (group == null) {
      log.warn("Group with id {} not found", id);
      return;
    }
    Discipline uniqueDiscipline = getUniqueDiscipline(discipline);
    uniqueDiscipline.getGroups().add(group);
    entityManager.persist(uniqueDiscipline);
  }

  @Transactional
  public void addProfessor(Long id, Professor professor) {
    log.trace("Adding professor {} {} {} to group with id {}", professor.getFirstName(),
        professor.getLastName(), professor.getPatronymic(), id);
    Group group = groupRepository.findById(id).orElse(null);
    if (group == null) {
      log.warn("Group with id {} not found", id);
      return;
    }
    Professor uniqueProfessor = getUniqueProfessor(professor);
    uniqueProfessor.getGroups().add(group);
    entityManager.persist(uniqueProfessor);
  }

  @Transactional
  public void removeDiscipline(Long id, Long discipline_id) {
    log.trace("Removing discipline with id {} from group with id {}", discipline_id, id);
    Group group = groupRepository.findById(id).orElse(null);
    Discipline discipline = entityManager.find(Discipline.class, discipline_id);
    if (group == null || discipline == null) {
      log.warn("Group with id {} not found or discipline with id {} not found", id, discipline_id);
      return;
    }
    discipline.getGroups().remove(group);
  }

  @Transactional
  public void removeProfessor(Long id, Long professor_id) {
    log.trace("Removing professor with id {} from group with id {}", professor_id, id);
    Group group = groupRepository.findById(id).orElse(null);
    Professor professor = entityManager.find(Professor.class, professor_id);
    if (group == null || professor == null) {
      log.warn("Group with id {} not found or professor with id {} not found", id, professor_id);
      return;
    }
    professor.getGroups().remove(group);
  }

  public Discipline getUniqueDiscipline(Discipline discipline) {
    List<?> disciplineList =
        entityManager.createQuery("SELECT d FROM Discipline d WHERE d.name = :name")
            .setParameter("name", discipline.getName()).getResultList();
    if (disciplineList.size() > 1) {
      log.warn("Multiple disciplines found for name {}", discipline.getName());
    }
    if (disciplineList.isEmpty()) {
      return discipline;
    }
    return (Discipline) disciplineList.getFirst();
  }

  public Professor getUniqueProfessor(Professor professor) {
    List<?> professorList = entityManager.createQuery(
            "SELECT p FROM Professor p WHERE p.firstName= :firstName AND p.lastName= :lastName AND p.patronymic=:patronymic")
        .setParameter("firstName", professor.getFirstName())
        .setParameter("lastName", professor.getLastName())
        .setParameter("patronymic", professor.getPatronymic()).getResultList();
    if (professorList.size() > 1) {
      log.warn("Multiple professors found for name {} {} {}", professor.getFirstName(),
          professor.getLastName(), professor.getPatronymic());
    }
    if (professorList.isEmpty()) {
      return professor;
    }
    return (Professor) professorList.getFirst();
  }
}
