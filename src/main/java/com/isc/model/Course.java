package com.isc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long courseId;
    @Column
    private String courseName;
    @Column
    private String teacher;
    @Column
    private String tuition;

    public Course() {

    }

    @Override
    public String toString() {
        return String.format(
                "Course[courseId=%d, courseName='%s', teacher='%s', tuition='%s']",
                courseId, courseName, teacher, tuition);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course that = (Course) o;
        return Objects.equals(courseId, that.courseId)
                && Objects.equals(courseName, that.courseName)
                && Objects.equals(teacher, that.teacher)
                && Objects.equals(tuition, that.tuition);
    }
}
