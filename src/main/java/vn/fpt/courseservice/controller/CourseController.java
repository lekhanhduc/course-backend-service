package vn.fpt.courseservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.fpt.courseservice.dto.request.CourseCreationRequest;
import vn.fpt.courseservice.dto.response.ApiResponse;
import vn.fpt.courseservice.dto.response.CourseCreationResponse;
import vn.fpt.courseservice.dto.response.CourseDetailResponse;
import vn.fpt.courseservice.dto.response.PageResponse;
import vn.fpt.courseservice.service.CourseService;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    ApiResponse<CourseCreationResponse> createCourse(@RequestBody CourseCreationRequest request) {

        var result = courseService.createCourse(request);
        return ApiResponse.<CourseCreationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Course Created")
                .result(result)
                .build();
    }

    @GetMapping
    ApiResponse<PageResponse<CourseDetailResponse>> getCourse(
            @RequestParam(required = false, defaultValue = "1") int currentPages,
            @RequestParam(required = false, defaultValue = "5") int pageSizes
    ) {
        var result = courseService.getCourses(currentPages,  pageSizes);
        return ApiResponse.<PageResponse<CourseDetailResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Course List")
                .result(result)
                .build();
    }

}
