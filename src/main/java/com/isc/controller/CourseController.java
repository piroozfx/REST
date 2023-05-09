package com.isc.controller;

import com.isc.Exception.CourseNotFoundException;
import com.isc.dto.CourseDto;
import com.isc.service.CourseService;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Log
@RestController
public class CourseController {

    final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/course/loadAll")
    public List<CourseDto> loadAllCourses() {
        return courseService.loadAllCourse();
    }

    @GetMapping("/course/load/{courseid}")
    public CourseDto loadCourse(@PathVariable("courseid") Long courseid) throws CourseNotFoundException {
        CourseDto foundCourse = courseService.loadCourseById(courseid);
        log.info("Course with Id={" + foundCourse.getCourseId() + "} was found successfully.");
        return foundCourse;
    }

    @DeleteMapping("/course/delete/{courseid}")
    public void deleteCourse(@PathVariable("courseid") Long courseid) throws CourseNotFoundException {
        courseService.delete(courseid);
        log.info("Course with Id={" + courseid + "} was deleted successfully.");
    }

    @PostMapping("/course/add")
    public Long saveCourse(@RequestBody CourseDto course) {
        CourseDto createdCourse = courseService.save(course);
        log.info("Course with Id={" + createdCourse.getCourseId() + "} was created successfully.");
        return createdCourse.getCourseId();
    }

    @PutMapping("/course/update/{courseid}")
    public CourseDto updateCourse(@RequestBody CourseDto course, @PathVariable Long courseid) throws CourseNotFoundException {
        CourseDto updatedCourse = courseService.update(course, courseid);
        log.info("Course with Id={" + updatedCourse.getCourseId() + "} was created successfully.");
        return updatedCourse;
    }
}