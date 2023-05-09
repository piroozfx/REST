package com.isc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.isc.Exception.CourseNotFoundException;
import com.isc.dto.CourseDto;
import com.isc.repository.CourseRepository;
import com.isc.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseService service;
    @Autowired
    private CourseRepository courseRepository;


    @BeforeEach
    void setup() {
        courseRepository.deleteAll();
    }

    private final Long courseId = 1L;
    private final String expectedErrMsg = "Course not found for this id: " + courseId;
    private final String baseUri = "/course";
    private final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Test
    public void createCourse() throws Exception {
        CourseDto expected = new CourseDto(courseId, "java", "akbari", "100");
        CourseDto inputParam = new CourseDto(courseId, "java", "akbari", "100");
        Mockito.when(service.save(inputParam)).thenReturn(expected);
        String jsonStr = ow.writeValueAsString(inputParam);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post(baseUri + "/add").content(jsonStr).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void loadAllCourses() throws Exception {

        Mockito.when(service.loadAllCourse()).thenReturn(
                List.of(CourseDto.builder().courseId(1L).courseName("java").teacher("Akbari").tuition("100").build()
                        , CourseDto.builder().courseId(2L).courseName("c++").teacher("mohamadi").tuition("150").build()));
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(baseUri + "/loadAll"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseName").value("java"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].teacher").value("Akbari"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tuition").value("100"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseName").value("c++"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].teacher").value("mohamadi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].tuition").value("150"));
    }

    @Test
    void loadCourse() throws Exception {
        Mockito.when(service.loadCourseById(courseId)).thenReturn(
                new CourseDto(courseId, "java", "akbari", "100")
        );
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(baseUri + "/load/{id}", courseId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseId").value(courseId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseName").value("java"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.teacher").value("akbari"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tuition").value("100"));
    }

    @Test
    public void updateCourseReturnsCourse() throws Exception {
        CourseDto inputParam = new CourseDto(courseId, "java", "akbari", "100");
        Mockito.when(service.update(inputParam, courseId)).thenReturn(inputParam);
        String jsonStr = ow.writeValueAsString(inputParam);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put(baseUri + "/update/{id}", courseId)
                        .content(jsonStr)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseId").value(courseId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseName").value("java"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.teacher").value("akbari"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tuition").value("100"));
    }

    @Test
    public void updateCourseWithWrongIdReturnsCourseNotFoundException() throws Exception {
        CourseDto inputParam = new CourseDto(courseId, "java", "akbari", "100");
        Mockito.when(service.update(inputParam, courseId)).thenThrow
                (new CourseNotFoundException(expectedErrMsg));
        String jsonStr = inputParam.toString();
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put(baseUri + "/update/{id}", courseId)
                        .content(jsonStr)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(result -> assertFalse(result.getResolvedException() instanceof CourseNotFoundException));
    }

    @Test
    public void deleteCourseReturnsAccepted() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete(baseUri + "/delete/{id}", courseId))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCourseWithWrongId() throws Exception {
        Mockito.when(service.delete(courseId)).thenThrow(
                new CourseNotFoundException(expectedErrMsg)
        );
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete(baseUri + "/delete/{id}", courseId))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CourseNotFoundException))
                .andExpect(result -> assertEquals(expectedErrMsg,
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }
}