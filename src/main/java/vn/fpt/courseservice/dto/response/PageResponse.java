package vn.fpt.courseservice.dto.response;

import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private int currentPages;
    private int pageSizes;
    private int totalPages;
    private long totalElements;
    private List<T> data;
}
