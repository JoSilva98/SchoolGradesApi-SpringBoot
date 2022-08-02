package school.schoolGrades.persistence.repository.extraTables;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.schoolGrades.persistence.model.extraTables.Grade;

import java.util.Optional;

@Repository
public interface GradeRepositoryI extends JpaRepository<Grade, Integer> {
    @Query(value = "SELECT * FROM grades " +
            "WHERE grades.student_id_fk = :studentId " +
            "AND grades.subject_id_fk = :subjectId", nativeQuery = true)
    Optional<Grade> findByStudentAndSubject(Long studentId, int subjectId);
}
