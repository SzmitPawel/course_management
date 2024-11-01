package course_management.domain.registration;

import course_management.domain.RegistrationEntity;
import course_management.repository.RegistrationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class RegistrationEntityTests {
    @Autowired
    private RegistrationRepository registrationRepository;

    @Test
    void create_entity_registration() {
        // given
        RegistrationEntity registrationEntity = new RegistrationEntity();

        // when
        registrationRepository.save(registrationEntity);

        // then
        assertEquals(1, registrationRepository.count());
    }

    @Test
    void read_entity_registration() {
        // given
        RegistrationEntity registrationEntity = new RegistrationEntity(LocalDateTime.now());

        registrationRepository.save(registrationEntity);

        // when
        Optional<RegistrationEntity> registrationEntityOptional = registrationRepository
                .findById(registrationEntity.getId());

        // then
        assertTrue(registrationEntityOptional.isPresent());
    }

    @Test
    void update_entity_registration() {
        // given
        RegistrationEntity registrationEntity = new RegistrationEntity(LocalDateTime.now());
        registrationRepository.save(registrationEntity);

        // when
        registrationEntity.setRegistrationDate(LocalDateTime.now().plusDays(1));

        Optional<RegistrationEntity> registrationEntityOptional = registrationRepository
                .findById(registrationEntity.getId());

        // then
        assertTrue(registrationEntityOptional.isPresent());
        assertEquals(registrationEntity.getRegistrationDate(),
                registrationEntityOptional.get().getRegistrationDate());
    }

    @Test
    void delete_entity_registration() {
        // given
        RegistrationEntity registrationEntity = new RegistrationEntity(LocalDateTime.now());
        registrationRepository.save(registrationEntity);

        // when
        registrationRepository.delete(registrationEntity);

        // then
        assertEquals(0, registrationRepository.count());
    }
}