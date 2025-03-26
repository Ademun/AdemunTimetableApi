package org.ademun.timetableapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "days", schema = "timetable_api")
public class Day {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "week", nullable = false)
  private Integer week;

  @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "group_id", nullable = false)
  private Group group;

  @OneToMany(mappedBy = "day")
  private Set<Lesson> lessons = new LinkedHashSet<>();

}
