package org.ademun.timetableapi.repository;

import org.ademun.timetableapi.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
  @Query("SELECT g FROM Group g where g.name = :groupName")
  List<Group> findByGroupName(String groupName);
}
