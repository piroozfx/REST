package com.isc.service;

import com.isc.Exception.CourseNotFoundException;
import com.isc.dto.CourseDto;
import com.isc.model.Course;

import java.util.List;

public interface CourseService {

    List<CourseDto> loadAllCourse();

    CourseDto loadCourseById(Long courseid) throws CourseNotFoundException;

    CourseDto save(CourseDto course);

    boolean delete(Long id) throws CourseNotFoundException;

    CourseDto update(CourseDto courseDto, Long courseId) throws CourseNotFoundException;
}
