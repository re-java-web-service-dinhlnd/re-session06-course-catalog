package com.re.coursecatalog.mapper.impl;

import com.re.coursecatalog.dto.request.CoursePatchRequestDto;
import com.re.coursecatalog.dto.request.CourseRequestDto;
import com.re.coursecatalog.dto.response.CourseResponseDto;
import com.re.coursecatalog.entity.Course;
import com.re.coursecatalog.mapper.CourseMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public Course toEntity(CourseRequestDto requestDto) {
        if(requestDto == null)  return null;

        Course course = new Course();
        course.setName(requestDto.getName());
        course.setDescription(requestDto.getDescription());
        course.setPrice(requestDto.getPrice());

        return course;
    }

    @Override
    public CourseResponseDto toResponseDto(Course course) {
        if(course == null)  return null;

        CourseResponseDto responseDto = new CourseResponseDto();
        responseDto.setId(course.getId());
        responseDto.setName(course.getName());
        responseDto.setDescription(course.getDescription());
        responseDto.setPrice(course.getPrice());
        responseDto.setImageUrl(course.getImageUrl());

        return responseDto;
    }

    @Override
    public void updateEntityFromRequest(CourseRequestDto requestDto, Course course) {
        if (requestDto == null || course == null) {
            return;
        }

        course.setName(requestDto.getName());
        course.setDescription(requestDto.getDescription());
        course.setPrice(requestDto.getPrice());
    }

    @Override
    public void updateEntityFromRequest(CoursePatchRequestDto requestDto, Course course) {
        if (requestDto == null || course == null) {
            return;
        }

        if (requestDto.getName() != null) {
            course.setName(requestDto.getName());
        }
        if (requestDto.getDescription() != null) {
            course.setDescription(requestDto.getDescription());
        }
        if (requestDto.getPrice() != null) {
            course.setPrice(requestDto.getPrice());
        }
    }
}
