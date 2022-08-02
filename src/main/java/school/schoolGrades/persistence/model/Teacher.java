package school.schoolGrades.persistence.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import school.schoolGrades.persistence.model.extraTables.Subject;

import javax.persistence.*;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "teachers")
@ToString
public class Teacher extends Person {

    private static Long teacherIdStatic = 2L;
    private Long teacherId = teacherIdStatic;

    @ManyToMany
    @JoinTable(
            name = "teacher_subject_list",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjectList;

    public void increaseTeacherId() {
        teacherIdStatic++;
    }
}
