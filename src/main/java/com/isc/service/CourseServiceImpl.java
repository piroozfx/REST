package com.isc.service;

import com.isc.Exception.CourseNotFoundException;
import com.isc.dto.CourseDto;
import com.isc.mapper.CourseMapper;
import com.isc.model.Course;
import com.isc.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    final CourseRepository courseRepository;
    final CourseMapper courseMapper;
    private final String errMsg = "course not found for this id: ";

    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    public List<CourseDto> loadAllCourse() {
        List<CourseDto> Courses = new ArrayList<CourseDto>();
        courseRepository.findAll().forEach(course1 -> Courses.add(courseMapper.fromEntity(course1)));
        return Courses;
    }

    @Override
    public CourseDto loadCourseById(Long courseid) throws CourseNotFoundException {
      return   courseMapper.fromEntity(courseRepository.findById(courseid)
                .orElseThrow(() -> new CourseNotFoundException(errMsg + courseid)));
//        return courseRepository.findById(courseid).get();
    }

    @Override
    public CourseDto save(CourseDto course) {
       return   courseMapper.fromEntity(courseRepository.save(courseMapper.fromModel(course)));
    }

    @Override
    public boolean delete(Long courseId) throws CourseNotFoundException {
        Course courseEntity = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(errMsg + courseId));

        courseRepository.delete(courseEntity);
        return true;
    }

    @Override
    public CourseDto update(CourseDto course, Long courseId) throws CourseNotFoundException {
        Course courseEntity = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(errMsg + courseId));
        courseEntity.setCourseName(course.getCourseName());
        courseEntity.setTeacher(course.getTeacher());
        courseEntity.setTuition(course.getTuition());
        return courseMapper.fromEntity(courseRepository.save(courseEntity));

    }

}
