package school.schoolGrades.persistence.model.extraTables;

import lombok.*;
import school.schoolGrades.persistence.model.Student;
import school.schoolGrades.persistence.model.Teacher;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private int id;

    @Column(nullable = false, unique = true)
    private String roleName;

    @OneToMany(mappedBy = "roleId")
    private List<Student> students;

    @OneToMany(mappedBy = "roleId")
    private List<Teacher> teachers;
}
