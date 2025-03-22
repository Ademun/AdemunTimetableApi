package org.ademun.timetableapi.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ademun.timetableapi.model.Lesson;
import org.ademun.timetableapi.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class LessonService {
    private final LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    public List<Lesson> all() {
        log.info("Retrieving all Lessons");
        return lessonRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Lesson::getId))
                .toList();
    }

    @Transactional
    public List<Lesson> byWeek(int week) {
        log.info("Retrieving Lessons by week {}", week);
        return lessonRepository
                .findAll()
                .stream()
                .filter(lesson -> lesson.getDay().getWeek() == week)
                .toList();
    }

    @Transactional
    public List<Lesson> byWeekDay(int week, int day) {
        log.info("Retrieving Lessons by week {} and day {}", week, day);
        return byWeek(week)
                .stream()
                .filter(lesson -> (lesson.getDay().getId() - (7L * (week - 1)) == day))
                .toList();
    }

    @Transactional
    public List<Lesson> byWeekDayOrder(int week, int day, int order) {
        log.info("Retrieving Lessons by week {} and day {} and order {}", week, day, order);
        return byWeekDay(week, day)
                .stream()
                .filter(lesson -> lesson.getOrder().getId() == (Integer) order)
                .toList();
    }

    @Transactional
    public Lesson one(Integer id) {
        log.info("Retrieving Lesson with id {}", id);
        return lessonRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Lesson create(Lesson lesson) {
        log.info("Creating Lesson: {}", lesson);
        return lessonRepository.save(lesson);
    }

    @Transactional
    public Lesson update(Integer id, Lesson lesson) {
        log.info("Updating Lesson with id: {} with Lesson: {}", id, lesson);
        Lesson old = lessonRepository.findById(id).orElseThrow();
        old.setClassroom(lesson.getClassroom());
        old.setProfessor(lesson.getProfessor());
        old.setDay(lesson.getDay());
        old.setOrder(lesson.getOrder());
        old.setDiscipline(lesson.getDiscipline());
        old.setType(lesson.getType());
        return lessonRepository.save(old);
    }

    @Transactional
    public Lesson delete(Integer id) {
        log.info("Deleting Lesson: {}", id);
        Lesson lesson = lessonRepository.findById(id).orElseThrow();
        lessonRepository.deleteById(id);
        return lesson;
    }
}
