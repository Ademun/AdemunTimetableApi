package org.ademun.timetableapi.repository;

import java.util.Optional;
import org.ademun.timetableapi.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

  @Query("SELECT g FROM Group g WHERE g.name = :groupName")
  Optional<Group> findByGroupName(String groupName);

  @Query("SELECT g FROM Group g WHERE g.channelId = :channelId")
  Optional<Group> findByChannelId(Long channelId);
}
