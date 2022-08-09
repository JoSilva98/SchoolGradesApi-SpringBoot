package school.schoolGrades.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.schoolGrades.persistence.model.Person;

import java.util.Optional;

@Repository
public interface PersonRepositoryI extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(String email);
}
