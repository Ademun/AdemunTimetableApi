package org.ademun.timetableapi.repository;

import org.ademun.timetableapi.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    @Query("SELECT lesson FROM Lesson lesson WHERE lesson.day.week = :week")
    List<Lesson> findLessonsByWeek(@Param("week") int week);

    @Query("SELECT lesson FROM Lesson lesson WHERE lesson.day.week = :week AND UPPER(lesson.day.name) = UPPER(:day)")
    List<Lesson> findLessonsByWeekDay(@Param("week") int week, @Param("day") String day);
}
