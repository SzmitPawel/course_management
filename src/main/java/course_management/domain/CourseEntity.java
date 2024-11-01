package course_management.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "COURSE")
@Getter
@Setter
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "Invalid title: Title is NULL.")
    @Size(min = 2, max = 20, message = "Title must be between 2 and 20 characters.")
    @Column(name = "title")
    private String title;

    @Size(min = 2, max = 255, message = "Description must be between 2 and 255.")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Invalid seat limit: Seat limit is NUll.")
    @Min(value = 2, message = "Seat limit cannot be below 2.")
    @Max(value = 10000, message = "Seat cannot be above 10000.")
    private int seatLimit;

    @OneToMany(
            targetEntity = RegistrationEntity.class,
            mappedBy = "course",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<RegistrationEntity> registrationList = null;


    public CourseEntity() {
    }

    public CourseEntity(final String title, final String description, final int seatLimit) {
        this.title = title;
        this.description = description;
        this.seatLimit = seatLimit;
    }

    public void addRegistration(RegistrationEntity registrationEntity) {
        if (registrationList == null) {
            registrationList = new ArrayList<>();
        }

        registrationList.add(registrationEntity);
        registrationEntity.setCourse(this);
    }

    public void removeRegistration(RegistrationEntity registrationEntity) {
        if (registrationList != null) {
            registrationList.remove(registrationEntity);
            registrationEntity.setCourse(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseEntity that = (CourseEntity) o;
        return seatLimit == that.seatLimit
                && Objects.equals(id, that.id)
                && Objects.equals(title, that.title)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, seatLimit);
    }
}