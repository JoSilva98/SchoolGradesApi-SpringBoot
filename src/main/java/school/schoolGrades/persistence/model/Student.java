package school.schoolGrades.persistence.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import school.schoolGrades.persistence.model.extraTables.Grade;
import school.schoolGrades.persistence.model.extraTables.Subject;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student extends Person {

    private static Long studentIdStatic = 2L;
    private Long studentId = studentIdStatic;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "student_subject_list",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjectList;

    @OneToMany(mappedBy = "studentId")
    private List<Grade> gradeList;

    public boolean addSubject(Subject subject) {
        return this.subjectList.add(subject);
    }

    public boolean removeSubject(Subject subject) {
        return this.subjectList.remove(subject);
    }

    public void increaseStudentId() {
        studentIdStatic++;
    }
}
