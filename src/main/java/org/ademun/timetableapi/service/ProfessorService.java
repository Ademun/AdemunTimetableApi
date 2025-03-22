package org.ademun.timetableapi.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ademun.timetableapi.model.Professor;
import org.ademun.timetableapi.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Transactional
    public List<Professor> all() {
        log.info("Retrieving all Professors");
        return professorRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Professor::getId))
                .toList();
    }

    @Transactional
    public Professor one(Integer id) {
        log.info("Retrieving Professor with id {}", id);
        return professorRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Professor create(Professor professor) {
        log.info("Creating Professor: {}", professor);
        return professorRepository.save(professor);
    }

    @Transactional
    public Professor update(Integer id, Professor professor) {
        log.info("Updating Professor with id: {} with Professor: {}", id, professor);
        Professor old = professorRepository.findById(id).orElseThrow();
        old.setFirstName(professor.getFirstName());
        old.setSecondName(professor.getSecondName());
        old.setPatronymic(professor.getPatronymic());
        old.setUrl(professor.getUrl());
        return professorRepository.save(old);
    }

    @Transactional
    public Professor delete(Integer id) {
        log.info("Deleting Professor: {}", id);
        Professor professor = professorRepository.findById(id).orElseThrow();
        professorRepository.deleteById(id);
        return professor;
    }
}
