package org.ademun.timetableapi.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ademun.timetableapi.model.Group;
import org.ademun.timetableapi.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Transactional
    public List<Group> all() {
        log.info("Retrieving all Groups");
        return groupRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Group::getId))
                .toList();
    }

    @Transactional
    public Group one(Integer id) {
        log.info("Retrieving Group with id {}", id);
        return groupRepository.findById(id)
                              .orElseThrow();
    }

    @Transactional
    public Group create(Group group) {
        log.info("Creating Group: {}", group);
        return groupRepository.save(group);
    }

    @Transactional
    public Group update(Integer id, Group group) {
        log.info("Updating Group with id: {} with Group: {}", id, group);
        Group old = groupRepository.findById(id)
                                   .orElseThrow();
        old.setName(group.getName());
        return groupRepository.save(old);
    }

    @Transactional
    public Group delete(Integer id) {
        log.info("Deleting Group: {}", id);
        Group group = groupRepository.findById(id)
                                     .orElseThrow();
        groupRepository.deleteById(id);
        return group;
    }
}
