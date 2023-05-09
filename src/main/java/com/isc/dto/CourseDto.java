package com.isc.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.isc.model.Course;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long courseId;
    private String courseName;
    private String teacher;
    private String tuition;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDto that = (CourseDto) o;
        return Objects.equals(courseId, that.courseId)
                && Objects.equals(courseName, that.courseName)
                && Objects.equals(teacher, that.teacher)
                && Objects.equals(tuition, that.tuition);
    }
}
