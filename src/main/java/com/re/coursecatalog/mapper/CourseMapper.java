package com.re.coursecatalog.mapper;

import com.re.coursecatalog.dto.request.CoursePatchRequestDto;
import com.re.coursecatalog.dto.request.CourseRequestDto;
import com.re.coursecatalog.dto.response.CourseResponseDto;
import com.re.coursecatalog.entity.Course;

public interface CourseMapper {
    Course toEntity(CourseRequestDto requestDto);

    CourseResponseDto toResponseDto(Course course);

    void updateEntityFromRequest(CourseRequestDto requestDto, Course course);
    void updateEntityFromRequest(CoursePatchRequestDto requestDto, Course course);
}
