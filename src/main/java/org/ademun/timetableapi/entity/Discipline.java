package org.ademun.timetableapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
  private Long disciplineId;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "url")
  private String url;
  @ManyToMany(mappedBy = "disciplines")
  @JsonIgnore
  private Set<Group> groups = new LinkedHashSet<>();
}
