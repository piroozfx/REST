package com.isc.service;

import com.isc.Exception.CourseNotFoundException;
import com.isc.dto.CourseDto;
import com.isc.model.Course;
import com.isc.repository.CourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CourseServiceTest {

    @Autowired
    CourseService courseService;

    @Autowired
    CourseRepository courseRepository;

    @Test
    public void findAllCourses() {
        createCourse();
        List<CourseDto> all = courseService.loadAllCourse();
        Assertions.assertEquals(1, all.size());
    }

    @Test
    public void findByIdWithExistingCourseReturnsCourse() throws CourseNotFoundException {
        createCourse();
        CourseDto course = courseService.loadCourseById(1L);
        Assertions.assertNotNull(course);
        Assertions.assertEquals(course.getCourseName(), "java");
        Assertions.assertEquals(course.getTeacher(), "akbari");
        Assertions.assertEquals(course.getTuition(), "100");
    }

    @Test
    public void createCourse() {
        courseService.save(new CourseDto(1L, "java", "akbari", "100"));

        List<CourseDto> all = courseService.loadAllCourse();
        Assertions.assertEquals(1, all.size());
    }

    @Test
    public void updateCourse() throws CourseNotFoundException {
        createCourse();
        CourseDto updatedCourse = courseService.update(
                new CourseDto(null ,"java", "akbari", "100"), 1L);
        CourseDto byId = courseService.loadCourseById(1L);
        Assertions.assertEquals(byId, updatedCourse);
    }

    @Test
    public void deleteCourseReturnsAccepted() throws CourseNotFoundException {
        createCourse();
        courseService.delete(1L);
        List<CourseDto> all = courseService.loadAllCourse();
        Assertions.assertEquals(0, all.size());
    }
}