package vn.fpt.courseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.fpt.courseservice.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
}
