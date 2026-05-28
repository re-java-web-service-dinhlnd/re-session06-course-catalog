package com.re.coursecatalog.dto.response;

import lombok.Data;

@Data
public class CourseResponseDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
}
