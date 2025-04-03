package org.ademun.timetableapi.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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
  public Group save(Group group) throws IllegalArgumentException {
    if (!groupRepository.findByGroupName(group.getName()).isEmpty()) {
      throw new IllegalArgumentException("Group with this name already exists");
    }
    return groupRepository.save(group);
  }

  @Transactional
  public List<Group> findAll() {
    return groupRepository.findAll();
  }

  @Transactional
  public Optional<Group> findById(Long id) {
    return groupRepository.findById(id);
  }

  @Transactional
  public void deleteById(Long id) {
    Group group = groupRepository.findById(id).orElse(null);
    if (group == null) {
      return;
    }
    group.getDisciplines().forEach(discipline -> discipline.getGroups().remove(group));
    group.getProfessors().forEach(professor -> professor.getGroups().remove(group));
    groupRepository.deleteById(id);
  }

  @Transactional
  public Set<Discipline> getDisciplines(Long id) {
    Group group = groupRepository.findById(id).orElse(null);
    if (group == null)
      return Collections.emptySet();
    return group.getDisciplines();
  }

  @Transactional
  public Set<Professor> getProfessors(Long id) {
    Group group = groupRepository.findById(id).orElse(null);
    if (group == null)
      return Collections.emptySet();
    return group.getProfessors();
  }

  @Transactional
  public void addDiscipline(Long id, Discipline discipline) {
    Group group = groupRepository.findById(id).orElse(null);
    if (group == null)
      return;
    Discipline uniqueDiscipline = getUniqueDiscipline(discipline);
    uniqueDiscipline.getGroups().add(group);
    entityManager.persist(uniqueDiscipline);
  }

  @Transactional
  public void addProfessor(Long id, Professor professor) {
    Group group = groupRepository.findById(id).orElse(null);
    if (group == null)
      return;
    Professor uniqueProfessor = getUniqueProfessor(professor);
    uniqueProfessor.getGroups().add(group);
    entityManager.persist(uniqueProfessor);
  }

  @Transactional
  public void removeDiscipline(Long id, Long discipline_id) {
    Group group = groupRepository.findById(id).orElse(null);
    Discipline discipline = entityManager.find(Discipline.class, discipline_id);
    if (group == null || discipline == null)
      return;
    discipline.getGroups().remove(group);
  }

  @Transactional
  public void removeProfessor(Long id, Long professor_id) {
    Group group = groupRepository.findById(id).orElse(null);
    Professor professor = entityManager.find(Professor.class, professor_id);
    if (group == null || professor == null)
      return;
    professor.getGroups().remove(group);
  }

  public Discipline getUniqueDiscipline(Discipline discipline) {
    List<?> disciplineList =
        entityManager.createQuery("SELECT d FROM Discipline d WHERE d.name = :name")
            .setParameter("name", discipline.getName()).getResultList();
    if (disciplineList.size() > 1) {
      //TODO: log
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
      //TODO: log
    }
    if (professorList.isEmpty()) {
      return professor;
    }
    return (Professor) professorList.getFirst();
  }
}
