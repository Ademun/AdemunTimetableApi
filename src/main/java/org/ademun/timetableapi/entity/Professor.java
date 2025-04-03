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
@Table(name = "professors")
public class Professor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long professor_id;
  @Column(name = "first_name", nullable = false)
  private String firstName;
  @Column(name = "last_name", nullable = false)
  private String lastName;
  @Column(name = "patronymic", nullable = false)
  private String patronymic;
  @Column(name = "url")
  private String url;
  @ManyToMany(mappedBy = "professors")
  @JsonBackReference
  private Set<Group> groups = new LinkedHashSet<>();
}
