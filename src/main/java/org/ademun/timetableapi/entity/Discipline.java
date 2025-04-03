package org.ademun.timetableapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "disciplines")
public class Discipline {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long discipline_id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "url")
  private String url;
  @ManyToMany(mappedBy = "disciplines")
  @JsonBackReference
  private Set<Group> groups = new LinkedHashSet<>();
}
