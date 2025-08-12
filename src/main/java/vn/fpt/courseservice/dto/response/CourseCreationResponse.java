package vn.fpt.courseservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CourseCreationResponse implements Serializable {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String image;
}
