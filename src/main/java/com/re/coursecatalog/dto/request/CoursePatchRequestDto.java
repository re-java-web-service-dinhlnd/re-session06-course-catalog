package com.re.coursecatalog.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CoursePatchRequestDto {
    @Size(min = 1, message = "Tên khóa học không được để trống")
    private String name;
    @Size(min = 1, message = "Mô tả khóa học không được để trống")
    private String description;
    // Giữ nguyên @Min, vì nó tự động bỏ qua nếu price là null
    @Min(value = 0, message = "Giá khóa học phải lớn hơn hoặc bằng 0")
    private Double price;
}
