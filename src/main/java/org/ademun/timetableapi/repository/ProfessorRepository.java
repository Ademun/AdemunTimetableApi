package org.ademun.timetableapi.repository;

import java.util.List;
import java.util.Optional;
import org.ademun.timetableapi.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

  @Query(
      "SELECT p FROM Professor p where p.firstName = :firstName AND p.lastName = :lastname AND p.patronymic = :patronymic")
  Optional<Professor> findByProfessorFullName(String firstName, String lastName, String patronymic);

  @Query("SELECT p FROM Professor p JOIN p.groups g WHERE g.name = :groupName")
  List<Professor> findByGroupName(String groupName);
}
