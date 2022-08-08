package school.schoolGrades.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.schoolGrades.persistence.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepositoryI extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);

    @Query(value = "SELECT * FROM students WHERE role_id_fk = :roleId", nativeQuery = true)
    List<Student> findByRoleId(int roleId);

    @Query(value = "SELECT * FROM students WHERE email LIKE %:email%", nativeQuery = true)
    List<Student> findLikeEmail(String email);

    @Query(value = """
            SELECT * FROM students
            ORDER BY students.id
            LIMIT :stuPerPage OFFSET :offset""", nativeQuery = true)
    List<Student> findPage(int stuPerPage, int offset);
}
