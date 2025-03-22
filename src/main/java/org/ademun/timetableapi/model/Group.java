package org.ademun.timetableapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "groups", schema = "timetable_api")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @OneToMany(mappedBy = "group")
    private Set<Day> days = new LinkedHashSet<>();

    @ManyToMany
    private Set<Discipline> disciplines = new LinkedHashSet<>();

    @ManyToMany
    private Set<Professor> professors = new LinkedHashSet<>();

}