package course_management.domain;

import course_management.repository.CourseEntityRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CourseEntityTest {
    @Autowired
    private CourseEntityRepository courseEntityRepository;

    private CourseEntity createCourseEntity() {
        return new CourseEntity("Title", "Description", 3);
    }

    @Nested
    class CreateEntity {
        @Test
        void create_entity_course() {
            // given
            CourseEntity course = createCourseEntity();

            // when
            courseEntityRepository.save(course);

            // then
            assertEquals(1, courseEntityRepository.count());
        }

        @Test
        void read_entity_course() {
            // given
            CourseEntity course = createCourseEntity();
            courseEntityRepository.save(course);

            // when
            Optional<CourseEntity> readCourseOpt = courseEntityRepository.findById(course.getId());


            // then
            assertTrue(readCourseOpt.isPresent());
            assertEquals(1, courseEntityRepository.count());
            assertEquals(course.getTitle(), readCourseOpt.get().getTitle());
            assertEquals(course.getDescription(), readCourseOpt.get().getDescription());
            assertEquals(course.getSeatLimit(), readCourseOpt.get().getSeatLimit());
        }

        @Test
        void update_entity_course() {
            // given
            CourseEntity course = createCourseEntity();
            courseEntityRepository.save(course);

            // when
            course.setTitle("New Title");
            course.setDescription("New Description");
            courseEntityRepository.save(course);

            Optional<CourseEntity> readCourseOpt = courseEntityRepository.findById(course.getId());

            // then
            assertTrue(readCourseOpt.isPresent());
            assertEquals(1, courseEntityRepository.count());
            assertEquals(course.getTitle(), readCourseOpt.get().getTitle());
            assertEquals(course.getDescription(),readCourseOpt.get().getDescription());
            assertEquals(course.getSeatLimit(), readCourseOpt.get().getSeatLimit());
        }

        @Test
        void delete_entity_course() {
            // given
            CourseEntity course = createCourseEntity();
            courseEntityRepository.save(course);

            // when
            courseEntityRepository.delete(course);

            // then
            assertEquals(0, courseEntityRepository.count());
        }
    }
}