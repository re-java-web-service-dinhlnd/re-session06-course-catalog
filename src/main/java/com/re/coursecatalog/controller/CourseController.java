package com.re.coursecatalog.controller;

import com.re.coursecatalog.dto.request.CourseRequestDto;
import com.re.coursecatalog.dto.response.ApiResponse;
import com.re.coursecatalog.dto.response.CourseResponseDto;
import com.re.coursecatalog.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CourseResponseDto>>> getCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<CourseResponseDto> responseDtos = courseService.getAllCoursesWithPagination(page, size);

        ApiResponse<Page<CourseResponseDto>> response = ApiResponse.<Page<CourseResponseDto>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách khóa học thành công")
                .data(responseDtos)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponseDto>> getCourseById(@PathVariable Long id){
        CourseResponseDto courseDetail = courseService.getCourseById(id);

        ApiResponse<CourseResponseDto> response = ApiResponse.<CourseResponseDto>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy chi tiết khóa học thành công")
                .data(courseDetail)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponseDto>> createCourse(
            @Valid @RequestBody CourseRequestDto requestDto
    ){
        CourseResponseDto newCourse = courseService.createCourse(requestDto);

        ApiResponse<CourseResponseDto> response = ApiResponse.<CourseResponseDto>builder()
                .status(HttpStatus.CREATED.value())
                .message("Tạo khóa học thành công")
                .data(newCourse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponseDto>> updateCourse(
            @PathVariable Long id, @Valid @RequestBody CourseRequestDto requestDto) {
        return ResponseEntity.ok(ApiResponse.<CourseResponseDto>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật toàn bộ khóa học thành công")
                .data(courseService.updateCourse(id, requestDto))
                .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponseDto>> patchCourse(
            @PathVariable Long id, @Valid @RequestBody com.re.coursecatalog.dto.request.CoursePatchRequestDto patchDto) {
        return ResponseEntity.ok(ApiResponse.<CourseResponseDto>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật một phần khóa học thành công")
                .data(courseService.patchCourse(id, patchDto))
                .build());
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<ApiResponse<Object>> uploadImage(
            @PathVariable Long id, @RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        try {
            CourseResponseDto updatedCourse = courseService.uploadCourseImage(id, file);
            return ResponseEntity.ok(ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Upload ảnh thành công")
                    .data(updatedCourse)
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage()).build());
        } catch (java.io.IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Lỗi khi lưu file ảnh: " + e.getMessage()).build());
        }
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/{id}/image")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        courseService.deleteCourseImage(id);
        return ResponseEntity.noContent().build();
    }
}
