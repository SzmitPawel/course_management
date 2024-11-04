package course_management.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "REGISTRATION")
@Getter
@Setter
public class RegistrationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_id")
    @Setter(AccessLevel.NONE)
    private Long id;

    // private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    private LocalDateTime registrationDate;

    public RegistrationEntity() {
    }

    public RegistrationEntity(final LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationEntity that = (RegistrationEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(course, that.course) && Objects.equals(registrationDate, that.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course, registrationDate);
    }
}