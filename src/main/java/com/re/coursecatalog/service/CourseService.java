package com.re.coursecatalog.service;

import com.re.coursecatalog.dto.request.CoursePatchRequestDto;
import com.re.coursecatalog.dto.request.CourseRequestDto;
import com.re.coursecatalog.dto.response.CourseResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CourseService {
    Page<CourseResponseDto> getAllCoursesWithPagination(int page, int size);

    CourseResponseDto getCourseById(Long id);

    CourseResponseDto createCourse(CourseRequestDto requestDto);

    CourseResponseDto updateCourse(Long id, CourseRequestDto requestDto);

    CourseResponseDto patchCourse(Long id, CoursePatchRequestDto patchDto);

    void deleteCourse(Long id);

    CourseResponseDto uploadCourseImage(Long id, MultipartFile file) throws IOException;

    void deleteCourseImage(Long id);
}
