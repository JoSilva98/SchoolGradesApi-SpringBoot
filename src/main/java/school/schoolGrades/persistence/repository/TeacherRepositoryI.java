package school.schoolGrades.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.schoolGrades.persistence.model.Teacher;

import java.util.List;

@Repository
public interface TeacherRepositoryI extends JpaRepository<Teacher, Long> {

    @Query(value = "SELECT * FROM teachers WHERE role_id_fk = :roleId", nativeQuery = true)
    List<Teacher> findByRoleId(int roleId);

    @Query(value = "SELECT * FROM teachers WHERE email LIKE %:email%", nativeQuery = true)
    List<Teacher> findLikeEmail(String email);

    @Query(value = """
            SELECT * FROM teachers
            ORDER BY teachers.id
            LIMIT :teaPerPage OFFSET :offset""", nativeQuery = true)
    List<Teacher> findPage(int teaPerPage, int offset);
}
