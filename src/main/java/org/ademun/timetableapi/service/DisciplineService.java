package org.ademun.timetableapi.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ademun.timetableapi.model.Discipline;
import org.ademun.timetableapi.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class DisciplineService {
    private final DisciplineRepository disciplineRepository;

    @Autowired
    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    @Transactional
    public List<Discipline> all() {
        log.info("Retrieving all Disciplines");
        return disciplineRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Discipline::getId))
                .toList();
    }

    @Transactional
    public Discipline one(Integer id) {
        log.info("Retrieving Discipline with id {}", id);
        return disciplineRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Discipline create(Discipline discipline) {
        log.info("Creating Discipline: {}", discipline);
        return disciplineRepository.save(discipline);
    }

    @Transactional
    public Discipline update(Integer id, Discipline discipline) {
        log.info("Updating Discipline with id: {} with Discipline: {}", id, discipline);
        Discipline old = disciplineRepository.findById(id).orElseThrow();
        old.setName(discipline.getName());
        old.setUrl(discipline.getUrl());
        return disciplineRepository.save(old);
    }

    @Transactional
    public Discipline delete(Integer id) {
        log.info("Deleting Discipline: {}", id);
        Discipline discipline = disciplineRepository.findById(id).orElseThrow();
        disciplineRepository.deleteById(id);
        return discipline;
    }
}
