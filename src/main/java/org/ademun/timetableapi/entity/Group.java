package org.ademun.timetableapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "groups")
public class Group {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long group_id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "channel_id")
  private Long channel_id;
  @ManyToMany
  @JoinTable(name = "group_discipline", joinColumns = @JoinColumn(name = "group_id"),
      inverseJoinColumns = @JoinColumn(name = "discipline_id"))
  @JsonManagedReference
  private Set<Discipline> disciplines = new LinkedHashSet<>();
  @ManyToMany
  @JoinTable(name = "group_professor", joinColumns = @JoinColumn(name = "group_id"),
      inverseJoinColumns = @JoinColumn(name = "professor_id"))
  @JsonManagedReference
  private Set<Professor> professors = new LinkedHashSet<>();
}

