package course_management.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class CourseEntityValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Nested
    class TitleField {

        @Test
        void validation_title_is_present() {
            // given
            CourseEntity courseEntity = new CourseEntity("Title", "Description", 3);

            // when
            Set<ConstraintViolation<CourseEntity>> violations = validator.validate(courseEntity);

            // then
            assertTrue(violations.isEmpty());
        }

        @Test
        void validation_title_is_not_present() {
            // given
            CourseEntity courseEntity = new CourseEntity(null, "Description", 3);

            // when
            Set<ConstraintViolation<CourseEntity>> violations = validator.validate(courseEntity);

            // then
            assertFalse(violations.isEmpty());
            assertEquals(1, violations.size());
            assertTrue(violations.iterator().next().getMessage()
                    .contains("Invalid title: Title is NULL."));
        }

        @Test
        void validation_title_length_is_below_minimum() {
            // given
            CourseEntity courseEntity = new CourseEntity("A", "Description", 3);

            // when
            Set<ConstraintViolation<CourseEntity>> violations = validator.validate(courseEntity);

            // then
            assertFalse(violations.isEmpty());
            assertEquals(1, violations.size());
            assertTrue(violations.iterator().next().getMessage()
                    .contains("Title must be between 2 and 20 characters."));
        }

        @Test
        void validation_title_length_is_above_maximum() {
            // given
            CourseEntity courseEntity = new CourseEntity("A".repeat(21), "Description", 3);

            // when
            Set<ConstraintViolation<CourseEntity>> violations = validator.validate(courseEntity);

            // then
            assertFalse(violations.isEmpty());
            assertEquals(1, violations.size());
            assertTrue(violations.iterator().next().getMessage()
                    .contains("Title must be between 2 and 20 characters."));
        }
    }

    @Nested
    class DescriptionField {

        @Test
        void validation_description_length_is_below_minimum() {
            // given
            CourseEntity courseEntity = new CourseEntity("Title", "A", 3);

            // when
            Set<ConstraintViolation<CourseEntity>> violations = validator.validate(courseEntity);

            // then
            assertFalse(violations.isEmpty());
            assertEquals(1, violations.size());
            assertTrue(violations.iterator().next().getMessage()
                    .contains("Description must be between 2 and 255."));
        }

        @Test
        void validation_description_length_is_above_maximum() {
            // given
            CourseEntity courseEntity = new CourseEntity("Title", "A".repeat(256), 3);

            // when
            Set<ConstraintViolation<CourseEntity>> violations = validator.validate(courseEntity);

            // then
            assertFalse(violations.isEmpty());
            assertEquals(1, violations.size());
            assertTrue(violations.iterator().next().getMessage()
                    .contains("Description must be between 2 and 255."));
        }
    }

    @Nested
    class SeatLimitField {

        @Test
        void validation_seat_limit_is_present() {
            // given
            CourseEntity courseEntity = new CourseEntity("Title", "Description", 3);

            // when
            Set<ConstraintViolation<CourseEntity>> violations = validator.validate(courseEntity);

            // then
            assertTrue(violations.isEmpty());
        }

        @Test
        void validation_seat_limit_is_below_minimum() {
            // given
            CourseEntity courseEntity = new CourseEntity("Title", "Description", 1);

            // when
            Set<ConstraintViolation<CourseEntity>> violations = validator.validate(courseEntity);

            // then
            assertFalse(violations.isEmpty());
            assertEquals(1, violations.size());
            assertTrue(violations.iterator().next().getMessage()
                    .contains("Seat limit cannot be below 2."));
        }

        @Test
        void validation_seat_limit_is_above_maximum() {
            // given
            CourseEntity courseEntity = new CourseEntity("Title", "Description", 10001);

            // when
            Set<ConstraintViolation<CourseEntity>> violations = validator.validate(courseEntity);

            // then
            assertFalse(violations.isEmpty());
            assertEquals(1, violations.size());
            assertTrue(violations.iterator().next().getMessage()
                    .contains("Seat cannot be above 10000."));
        }
    }
}