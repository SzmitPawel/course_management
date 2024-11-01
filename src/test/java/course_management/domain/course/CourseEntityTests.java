package course_management.domain.course;

import course_management.domain.CourseEntity;
import course_management.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CourseEntityTests {
    @Autowired
    private CourseRepository courseRepository;

    private CourseEntity createCourseEntity() {
        return new CourseEntity("Title", "Description", 3);
    }

    @Test
    void create_entity_course() {
        // given
        CourseEntity course = createCourseEntity();

        // when
        courseRepository.save(course);

        // then
        assertEquals(1, courseRepository.count());
    }

    @Test
    void read_entity_course() {
        // given
        CourseEntity course = createCourseEntity();
        courseRepository.save(course);

        // when
        Optional<CourseEntity> readCourseOpt = courseRepository.findById(course.getId());


        // then
        assertTrue(readCourseOpt.isPresent());
        assertEquals(1, courseRepository.count());
        assertEquals(course.getTitle(), readCourseOpt.get().getTitle());
        assertEquals(course.getDescription(), readCourseOpt.get().getDescription());
        assertEquals(course.getSeatLimit(), readCourseOpt.get().getSeatLimit());
    }

    @Test
    void update_entity_course() {
        // given
        CourseEntity course = createCourseEntity();
        courseRepository.save(course);

        // when
        course.setTitle("New Title");
        course.setDescription("New Description");
        courseRepository.save(course);

        Optional<CourseEntity> readCourseOpt = courseRepository.findById(course.getId());

        // then
        assertTrue(readCourseOpt.isPresent());
        assertEquals(1, courseRepository.count());
        assertEquals(course.getTitle(), readCourseOpt.get().getTitle());
        assertEquals(course.getDescription(), readCourseOpt.get().getDescription());
        assertEquals(course.getSeatLimit(), readCourseOpt.get().getSeatLimit());
    }

    @Test
    void delete_entity_course() {
        // given
        CourseEntity course = createCourseEntity();
        courseRepository.save(course);

        // when
        courseRepository.delete(course);

        // then
        assertEquals(0, courseRepository.count());
    }
}