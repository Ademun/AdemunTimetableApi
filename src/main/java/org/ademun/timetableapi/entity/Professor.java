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
@Table(name = "professors")
public class Professor {

  @Id
  @SequenceGenerator(name = "seq_id_professor", sequenceName = "seq_id_professor",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_professor")
  private Long professorId;
  @Column(name = "first_name", nullable = false)
  private String firstName;
  @Column(name = "last_name", nullable = false)
  private String lastName;
  @Column(name = "patronymic", nullable = false)
  private String patronymic;
  @Column(name = "url")
  private String url;
  @ManyToMany
  @JoinTable(name = "group_professor", joinColumns = @JoinColumn(name = "professor_id"),
      inverseJoinColumns = @JoinColumn(name = "group_id"))
  @JsonIgnore
  private Set<Group> groups = new LinkedHashSet<>();

  public String getFullName() {
    return firstName + " " + lastName + " " + patronymic;
  }
}
