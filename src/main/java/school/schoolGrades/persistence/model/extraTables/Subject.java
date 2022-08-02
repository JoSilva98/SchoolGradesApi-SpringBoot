package school.schoolGrades.persistence.model.extraTables;

import lombok.*;
import school.schoolGrades.persistence.model.Student;
import school.schoolGrades.persistence.model.Teacher;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private int id;

    @Column(nullable = false, unique = true)
    private String subjectName;

    @ManyToMany(mappedBy = "subjectList")
    private List<Student> studentList = new ArrayList<>();

    @ManyToMany(mappedBy = "subjectList")
    private List<Teacher> teacherList = new ArrayList<>();

    @OneToMany(mappedBy = "subjectId")
    private List<Grade> gradeList;
}
