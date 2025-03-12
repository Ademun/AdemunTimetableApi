package org.ademun.timetableapi.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.ademun.timetableapi.entity.Discipline;
import org.ademun.timetableapi.entity.Lesson;
import org.ademun.timetableapi.entity.Order;
import org.ademun.timetableapi.repository.DisciplineRepository;
import org.ademun.timetableapi.repository.LessonRepository;
import org.ademun.timetableapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final OrderRepository orderRepository;
    private final DisciplineRepository disciplineRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository, OrderRepository orderRepository, DisciplineRepository disciplineRepository) {
        this.lessonRepository = lessonRepository;
        this.orderRepository = orderRepository;
        this.disciplineRepository = disciplineRepository;
    }

    @Transactional
    public Lesson createLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Transactional(rollbackOn = EntityNotFoundException.class)
    public Lesson deleteLesson(int id) throws EntityNotFoundException {
        Lesson deleted = lessonRepository.getReferenceById(id);
        if (deleted == null) {
            throw new EntityNotFoundException();
        }
        lessonRepository.delete(deleted);
        return deleted;
    }

    @Transactional(rollbackOn = EntityNotFoundException.class)
    public Lesson updateLesson(Lesson lesson) throws EntityNotFoundException {
        Lesson updated = lessonRepository.getReferenceById(lesson.getId());
        if (updated == null) {
            throw new EntityNotFoundException();
        }
        return lessonRepository.save(lesson);
    }

    public Lesson findLessonById(int id) throws EntityNotFoundException {
        Lesson lesson = lessonRepository.getReferenceById(id);
        if (lesson == null) {
            throw new EntityNotFoundException();
        }
        return lesson;
    }

    public List<Lesson> findAllLessons() {
        return lessonRepository.findAll();
    }

    @Transactional(rollbackOn = EntityNotFoundException.class)
    public List<Lesson> findLessonsByWeek(int week) {
        List<Lesson> lessons = lessonRepository.findLessonsByWeek(week);
        if (lessons == null) {
            throw new EntityNotFoundException();
        }
        return lessons;
    }

    @Transactional(rollbackOn = EntityNotFoundException.class)
    public List<Lesson> findLessonsByWeekDay(int week, String day) {
        List<Lesson> lessons = lessonRepository.findLessonsByWeekDay(week, day);
        if (lessons == null) {
            throw new EntityNotFoundException();
        }
        return lessons;
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public Discipline addDiscipline(Discipline discipline) {
        return disciplineRepository.save(discipline);
    }

}
