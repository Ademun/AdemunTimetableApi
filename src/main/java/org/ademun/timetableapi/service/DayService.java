package org.ademun.timetableapi.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ademun.timetableapi.model.Day;
import org.ademun.timetableapi.repository.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class DayService {
    private final DayRepository dayRepository;

    @Autowired
    public DayService(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    @Transactional
    public List<Day> all() {
        log.info("Retrieving all Days");
        return dayRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Day::getId))
                .toList();
    }

    @Transactional
    public Day one(Integer id) {
        log.info("Retrieving Day with id {}", id);
        return dayRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Day create(Day day) {
        log.info("Creating Day: {}", day);
        return dayRepository.save(day);
    }

    @Transactional
    public Day update(Integer id, Day day) {
        log.info("Updating Day with id: {} with Day: {}", id, day);
        Day old = dayRepository.findById(id).orElseThrow();
        old.setName(day.getName());
        old.setWeek(day.getWeek());
        return dayRepository.save(old);
    }

    @Transactional
    public Day delete(Integer id) {
        log.info("Deleting Day: {}", id);
        Day day = dayRepository.findById(id).orElseThrow();
        dayRepository.deleteById(id);
        return day;
    }
}
