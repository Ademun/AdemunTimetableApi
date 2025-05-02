package org.ademun.timetableapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "groups")
public class Group {

  @Id
  @SequenceGenerator(name = "seq_id_group", sequenceName = "seq_id_group", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_group")
  @Column(name = "group_id")
  private Long groupId;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "channel_id")
  private Long channelId;
  @ManyToMany
  @JoinTable(name = "group_discipline", joinColumns = @JoinColumn(name = "group_id"),
      inverseJoinColumns = @JoinColumn(name = "discipline_id"))
  @JsonIgnore
  private Set<Discipline> disciplines = new LinkedHashSet<>();
  @ManyToMany
  @JoinTable(name = "group_professor", joinColumns = @JoinColumn(name = "group_id"),
      inverseJoinColumns = @JoinColumn(name = "professor_id"))
  @JsonIgnore
  private Set<Professor> professors = new LinkedHashSet<>();
}

