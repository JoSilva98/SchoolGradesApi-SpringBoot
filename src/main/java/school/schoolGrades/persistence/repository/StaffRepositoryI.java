package school.schoolGrades.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.schoolGrades.persistence.model.Staff;

import java.util.List;

@Repository
public interface StaffRepositoryI extends JpaRepository<Staff, Long> {
    @Query(value = "SELECT * FROM staff WHERE role_id_fk = :roleId", nativeQuery = true)
    List<Staff> findByRoleId(int roleId);

    @Query(value = "SELECT * FROM staff WHERE email LIKE %:email%", nativeQuery = true)
    List<Staff> findLikeEmail(String email);
}
