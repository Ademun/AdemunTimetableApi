package org.ademun.timetableapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders", schema = "timetable_api")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "day_order", nullable = false)
  private Integer dayOrder;

  @Column(name = "time_start", nullable = false)
  private LocalTime timeStart;

  @Column(name = "time_end", nullable = false)
  private LocalTime timeEnd;

  @OneToMany(mappedBy = "order")
  private Set<Lesson> lessons = new LinkedHashSet<>();

}
