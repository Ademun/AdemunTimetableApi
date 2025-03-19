package org.ademun.timetableapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "professors", schema = "timetable")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "second_name", nullable = false, length = Integer.MAX_VALUE)
    private String secondName;

    @Column(name = "third_name", nullable = false, length = Integer.MAX_VALUE)
    private String thirdName;

    @Column(name = "url", length = Integer.MAX_VALUE)
    private String url;

    @OneToMany(mappedBy = "professor")
    private Set<Lesson> lessons = new LinkedHashSet<>();

}