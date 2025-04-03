package org.ademun.timetableapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
  @SequenceGenerator(name = "seq_id_group", sequenceName = "seq_id_group", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_group")
  private Long group_id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "channel_id")
  private Long channel_id;
  @ManyToMany(mappedBy = "groups")
  @JsonIgnore
  private Set<Discipline> disciplines = new LinkedHashSet<>();
  @ManyToMany(mappedBy = "groups")
  @JsonIgnore
  private Set<Professor> professors = new LinkedHashSet<>();
}

