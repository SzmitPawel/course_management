package course_management.domain.course;

import course_management.domain.CourseEntity;
import course_management.domain.RegistrationEntity;
import course_management.repository.CourseRepository;
import course_management.repository.RegistrationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CourseRegistrationRelationTests {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private RegistrationRepository registrationRepository;

    @Test
    void add_registration_to_course() {
        // given
        RegistrationEntity registration = new RegistrationEntity(LocalDateTime.now());
        CourseEntity course = new CourseEntity("Title", "Description", 2);

        course.addRegistration(registration);
        courseRepository.save(course);

        // when
        Optional<CourseEntity> courseOptional = courseRepository.findById(course.getId());

        // then
        assertTrue(courseOptional.isPresent());
        assertEquals(1,courseOptional.get().getRegistrationList().size());
    }

    @Test
    void remove_registration_from_course() {
        // given
        RegistrationEntity registration = new RegistrationEntity(LocalDateTime.now());
        CourseEntity course = new CourseEntity("Title", "Description", 2);

        course.addRegistration(registration);
        courseRepository.save(course);

        // when
        course.removeRegistration(registration);
        Optional<CourseEntity> courseOptional = courseRepository.findById(course.getId());

        // then
        assertTrue(courseOptional.isPresent());
        assertEquals(0,courseOptional.get().getRegistrationList().size());
    }

    @Test
    void delete_course_and_orphanRemoval() {
        // given
        CourseEntity course = new CourseEntity("Title", "Description", 2);
        RegistrationEntity registration1 = new RegistrationEntity(LocalDateTime.now());
        RegistrationEntity registration2 = new RegistrationEntity(LocalDateTime.now());

        course.addRegistration(registration1);
        course.addRegistration(registration2);
        courseRepository.save(course);

        // when
        course.removeRegistration(registration1);

        // then
        assertEquals(1, registrationRepository.count());
    }
}