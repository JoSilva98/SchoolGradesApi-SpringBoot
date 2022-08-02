package school.schoolGrades.persistence.repository.extraTables;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.schoolGrades.persistence.model.extraTables.Subject;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface SubjectRepositoryI extends JpaRepository<Subject, Integer> {
    @Query(value = "SELECT * FROM subjects " +
            "WHERE UPPER(subjects.subject_name) = UPPER(:subjectName)", nativeQuery = true)
    Optional<Subject> findBySubjectName(String subjectName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM student_subject_list " +
            "WHERE subject_id = :subjectId", nativeQuery = true)
    void deleteFromStudents(int subjectId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM teacher_subject_list " +
            "WHERE subject_id = :subjectId", nativeQuery = true)
    void deleteFromTeachers(int subjectId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM subjects " +
            "WHERE subjects.id = :subjectId", nativeQuery = true)
    void deleteSubject(int subjectId);
}
