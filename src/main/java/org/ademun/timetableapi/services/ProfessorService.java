package org.ademun.timetableapi.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.ademun.timetableapi.entity.Professor;
import org.ademun.timetableapi.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional
    public Professor create(Professor professor) {
        return professorRepository.save(professor);
    }

    @Transactional(rollbackOn = EntityNotFoundException.class)
    public Professor delete(int id) throws EntityNotFoundException {
        Professor deleted = professorRepository.getReferenceById(id);
        if (deleted == null) {
            throw new EntityNotFoundException();
        }
        professorRepository.delete(deleted);
        return deleted;
    }

    @Transactional(rollbackOn = EntityNotFoundException.class)
    public Professor update(Professor professor) throws EntityNotFoundException {
        Professor updated = professorRepository.getReferenceById(professor.getId());
        if (updated == null) {
            throw new EntityNotFoundException();
        }
        return professorRepository.save(professor);
    }

    public Professor getReferenceById(int id) throws EntityNotFoundException {
        Professor professor = professorRepository.getReferenceById(id);
        if (professor == null) {
            throw new EntityNotFoundException();
        }
        return professor;
    }

    public List<Professor> findAll() {
        return professorRepository.findAll();
    }
}
