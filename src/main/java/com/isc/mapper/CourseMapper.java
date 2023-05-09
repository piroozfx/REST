package com.isc.mapper;

import com.isc.dto.CourseDto;
import com.isc.model.Course;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class CourseMapper {


    public Course fromModel(CourseDto courseDto) {
        return Course.builder()
                .courseName(courseDto.getCourseName())
                .teacher(courseDto.getTeacher())
                .tuition(courseDto.getTuition())
                .build();
    }

    public CourseDto fromEntity(Course CourseEntity) {
        return CourseDto.builder()
                .courseId(CourseEntity.getCourseId())
                .courseName(CourseEntity.getCourseName())
                .teacher(CourseEntity.getTeacher())
                .tuition(CourseEntity.getTuition())
                .build();
    }
}