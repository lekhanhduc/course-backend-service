package vn.fpt.courseservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.fpt.courseservice.dto.request.CourseCreationRequest;
import vn.fpt.courseservice.dto.response.CourseCreationResponse;
import vn.fpt.courseservice.dto.response.CourseDetailResponse;
import vn.fpt.courseservice.dto.response.PageResponse;
import vn.fpt.courseservice.model.Course;
import vn.fpt.courseservice.model.User;
import vn.fpt.courseservice.repository.CourseRepository;
import vn.fpt.courseservice.repository.UserRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseCreationResponse createCourse(CourseCreationRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        Course course = Course.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .courseCover(request.getImage())
                .author(user)
                .build();

        courseRepository.save(course);

        return CourseCreationResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .price(course.getPrice())
                .image(course.getCourseCover())
                .build();
    }

    public PageResponse<CourseDetailResponse> getCourses(int currentPage, int pageSize) {

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Course> coursePage = courseRepository.findAll(pageable);
        List<Course> content = coursePage.getContent();

        return PageResponse.<CourseDetailResponse>builder()
                .currentPages(currentPage)
                .pageSizes(pageSize)
                .totalPages(coursePage.getTotalPages())
                .totalElements(coursePage.getTotalElements())
                .data(content
                        .stream()
                        .map(course -> CourseDetailResponse.builder()
                                .id(course.getId())
                                .name(course.getName())
                                .description(course.getDescription())
                                .price(course.getPrice())
                                .image(course.getCourseCover())
                                .build())
                        .toList())
                .build();
    }

}
