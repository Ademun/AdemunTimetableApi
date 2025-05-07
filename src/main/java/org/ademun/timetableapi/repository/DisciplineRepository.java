package org.ademun.timetableapi.repository;

import java.util.List;
import java.util.Optional;
import org.ademun.timetableapi.entity.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

  @Query("SELECT g FROM Discipline g where REPLACE(LOWER(g.name), ' ', '') = REPLACE(LOWER(:disciplineName), ' ', '')")
  Optional<Discipline> findDisciplineByName(String disciplineName);

  @Query("SELECT d FROM Discipline d JOIN d.groups g WHERE REPLACE(LOWER(g.name), ' ', '') = REPLACE(LOWER(:groupName), ' ', '')")
  List<Discipline> findByGroupName(String groupName);
}
