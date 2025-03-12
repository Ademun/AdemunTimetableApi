package org.ademun.timetableapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ademun.timetableapi.entity.Lesson;
import org.ademun.timetableapi.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/timetable")
public class TimetableController {
    private final LessonService lessonService;

    private final ObjectMapper objectMapper;

    @Autowired
    public TimetableController(LessonService lessonService, ObjectMapper objectMapper) {
        this.lessonService = lessonService;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = "/", produces = "application/json")
    public String allEntries() {
        log.info("Requested all entries from timetable");
        List<Lesson> lessons = lessonService.findAllLessons();
        List<String> list = serializeLessons(lessons);
        log.info("Successfully found {} entries", list.size());
        return list.toString();
    }

    @GetMapping(value = "/{week}", produces = "application/json")
    public String allEntriesOnWeek(@PathVariable int week) {
        log.info("Requested all entries on week {}", week);
        List<Lesson> lessons = lessonService.findLessonsByWeek(week);
        List<String> list = serializeLessons(lessons);
        log.info("Successfully found {} entries", list.size());
        return list.toString();
    }

    @GetMapping(value = "/{week}/{day}", produces = "application/json")
    public String entryOnWeek(@PathVariable int week, @PathVariable String day) {
        log.info("Requested entry on week {} on day {}", week, day);
        List<Lesson> lessons = lessonService.findLessonsByWeekDay(week, day);
        List<String> list = serializeLessons(lessons);
        log.info("Successfully found {} entries", list.size());
        return list.toString();
    }

    private List<String> serializeLessons(List<Lesson> lessons) {
        return lessons.stream().map(lesson -> {
            try {
                return objectMapper.writeValueAsString(lesson);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }).toList();
    }
}
