package org.ademun.timetableapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "professors", schema = "timetable_api")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = Integer.MAX_VALUE)
    private String firstName;

    @Column(name = "second_name", nullable = false, length = Integer.MAX_VALUE)
    private String secondName;

    @Column(name = "patronymic", nullable = false, length = Integer.MAX_VALUE)
    private String patronymic;

    @Column(name = "url", length = Integer.MAX_VALUE)
    private String url;

    @ManyToMany(mappedBy = "professors")
    private Set<Group> groups = new LinkedHashSet<>();

    @OneToMany(mappedBy = "professor")
    private Set<Lesson> lessons = new LinkedHashSet<>();

}