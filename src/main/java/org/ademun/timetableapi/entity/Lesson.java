package org.ademun.timetableapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Lessons")
@Getter
@Setter
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LessonType lesson_type;
    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;
    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;
    @ManyToOne
    @JoinColumn(name = "day_id", nullable = false)
    private Day day;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    public enum LessonType {
        PRACTICE, LECTURE
    }
}
