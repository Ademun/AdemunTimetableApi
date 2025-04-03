package org.ademun.timetableapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "disciplines")
public class Discipline {
  @Id
  @SequenceGenerator(name = "seq_id_discipline", sequenceName = "seq_id_discipline",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_discipline")
  private Long discipline_id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "url")
  private String url;
  @ManyToMany
  @JoinTable(name = "group_discipline", joinColumns = @JoinColumn(name = "discipline_id"),
      inverseJoinColumns = @JoinColumn(name = "group_id"))
  @JsonIgnore
  private Set<Group> groups = new LinkedHashSet<>();
}
