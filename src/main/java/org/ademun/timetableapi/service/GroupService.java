package org.ademun.timetableapi.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ademun.timetableapi.model.Discipline;
import org.ademun.timetableapi.model.Group;
import org.ademun.timetableapi.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

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
  public List<Group> all() {
    log.info("Retrieving all Groups");
    return groupRepository.findAll().stream().sorted(Comparator.comparing(Group::getId)).toList();
  }

  @Transactional
  public Group one(Integer id) {
    log.info("Retrieving Group with id {}", id);
    return groupRepository.findById(id).orElseThrow();
  }

  @Transactional
  public Group create(Group group) {
    List<Discipline> disciplines = getAccordingDisciplines(group);
    if (!disciplines.isEmpty()) {
      group.setDisciplines(new LinkedHashSet<>(disciplines));
      return groupRepository.save(group);
    }
    log.info("Creating Group: {}", group);
    return groupRepository.save(group);
  }

  private List<Discipline> getAccordingDisciplines(Group group) {
    List<String> disciplineNames =
        group.getDisciplines().stream().map(Discipline::getName).toList();
    return entityManager.createQuery("SELECT d FROM Discipline d WHERE d.name IN :disciplineNames",
        Discipline.class).setParameter("disciplineNames", disciplineNames).getResultList();
  }

  @Transactional
  public Group update(Integer id, Group group) {
    log.info("Updating Group with id: {} with Group: {}", id, group);
    Group old = groupRepository.findById(id).orElseThrow();
    old.setName(group.getName());
    return groupRepository.save(old);
  }

  @Transactional
  public Group delete(Integer id) {
    log.info("Deleting Group: {}", id);
    Group group = groupRepository.findById(id).orElseThrow();
    List<Discipline> disciplines = getAccordingDisciplines(group);
    groupRepository.deleteById(id);
    for (Discipline discipline : disciplines) {
      group.getDisciplines().remove(discipline);
    }
    return group;
  }
}
