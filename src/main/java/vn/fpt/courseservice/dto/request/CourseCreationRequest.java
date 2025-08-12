package vn.fpt.courseservice.dto.request;

import lombok.Getter;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
public class CourseCreationRequest implements Serializable {
    private String name;
    private String description;
    private BigDecimal price;
    private String image;
}
