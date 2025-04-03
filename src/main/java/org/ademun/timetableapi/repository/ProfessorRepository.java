package org.ademun.timetableapi.repository;

import org.ademun.timetableapi.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
  @Query(
      "SELECT p FROM Professor p where p.firstName = :firstName AND p.lastName = :lastname AND p.patronymic = :patronymic")
  List<Professor> findByProfessorFullName(String firstName, String lastName, String patronymic);
}
