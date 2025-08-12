package vn.fpt.courseservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CourseDetailResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String image;
}
