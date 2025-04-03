package org.ademun.timetableapi.repository;

import org.ademun.timetableapi.entity.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
  @Query("SELECT g FROM Discipline g where g.name = :disciplineName")
  List<Discipline> findByDisciplineName(String disciplineName);
}
