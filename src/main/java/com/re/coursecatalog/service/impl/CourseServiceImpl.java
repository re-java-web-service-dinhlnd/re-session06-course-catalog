package com.re.coursecatalog.service.impl;

import com.re.coursecatalog.dto.request.CoursePatchRequestDto;
import com.re.coursecatalog.dto.request.CourseRequestDto;
import com.re.coursecatalog.dto.response.CourseResponseDto;
import com.re.coursecatalog.entity.Course;
import com.re.coursecatalog.exception.ResourceNotFoundException;
import com.re.coursecatalog.mapper.CourseMapper;
import com.re.coursecatalog.repository.CourseRepository;
import com.re.coursecatalog.service.CourseService;
import com.re.coursecatalog.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final FileService fileService;

    @Override
    public Page<CourseResponseDto> getAllCoursesWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Course> coursePage = courseRepository.findAll(pageable);

        return coursePage.map(courseMapper::toResponseDto);
    }

    @Override
    public CourseResponseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khóa học với ID: " + id));

        return courseMapper.toResponseDto(course);
    }

    @Override
    public CourseResponseDto createCourse(CourseRequestDto requestDto) {
        Course courseEntity = courseMapper.toEntity(requestDto);

        Course saveCourse = courseRepository.save(courseEntity);

        return courseMapper.toResponseDto(saveCourse);
    }

    @Override
    public CourseResponseDto updateCourse(Long id, CourseRequestDto requestDto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khóa học với ID: " + id));

        courseMapper.updateEntityFromRequest(requestDto, course);
        return courseMapper.toResponseDto(courseRepository.save(course));
    }

    @Override
    public CourseResponseDto patchCourse(Long id, CoursePatchRequestDto patchDto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khóa học với ID: " + id));

        courseMapper.updateEntityFromRequest(patchDto, course);
        return courseMapper.toResponseDto(courseRepository.save(course));
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khóa học với ID: " + id));
        
        fileService.deleteFile(course.getImageUrl());
        courseRepository.delete(course);
    }

    @Override
    public CourseResponseDto uploadCourseImage(Long id, MultipartFile file) throws IOException {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khóa học với ID: " + id));

        fileService.deleteFile(course.getImageUrl());

        String fileName = fileService.saveFile(file);
        
        course.setImageUrl(fileName);
        return courseMapper.toResponseDto(courseRepository.save(course));
    }

    @Override
    public void deleteCourseImage(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khóa học với ID: " + id));

        if (course.getImageUrl() == null) {
            throw new ResourceNotFoundException("Khóa học này chưa có ảnh");
        }

        fileService.deleteFile(course.getImageUrl());
        course.setImageUrl(null);
        courseRepository.save(course);
    }
}
