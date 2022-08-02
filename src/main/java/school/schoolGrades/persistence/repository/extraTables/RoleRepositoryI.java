package school.schoolGrades.persistence.repository.extraTables;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.schoolGrades.persistence.model.extraTables.Role;

@Repository
public interface RoleRepositoryI extends JpaRepository<Role, Integer> {
}
