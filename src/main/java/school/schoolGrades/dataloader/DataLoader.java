package school.schoolGrades.dataloader;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import school.schoolGrades.persistence.model.Staff;
import school.schoolGrades.persistence.model.Student;
import school.schoolGrades.persistence.model.Teacher;
import school.schoolGrades.persistence.model.extraTables.Grade;
import school.schoolGrades.persistence.model.extraTables.Role;
import school.schoolGrades.persistence.model.extraTables.Subject;
import school.schoolGrades.persistence.repository.StaffRepositoryI;
import school.schoolGrades.persistence.repository.StudentRepositoryI;
import school.schoolGrades.persistence.repository.TeacherRepositoryI;
import school.schoolGrades.persistence.repository.extraTables.GradeRepositoryI;
import school.schoolGrades.persistence.repository.extraTables.RoleRepositoryI;
import school.schoolGrades.persistence.repository.extraTables.SubjectRepositoryI;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final StudentRepositoryI studentRepository;
    private final TeacherRepositoryI teacherRepository;
    private final StaffRepositoryI staffRepository;
    private final RoleRepositoryI roleRepository;
    private final SubjectRepositoryI subjectRepository;
    private final GradeRepositoryI gradeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        //Roles
        Role studentRole = Role.builder()
                .roleName("STUDENT")
                .build();

        Role teacherRole = Role.builder()
                .roleName("TEACHER")
                .build();

        Role staffRole = Role.builder()
                .roleName("STAFF")
                .build();

        List<Role> roles = List.of(studentRole, teacherRole, staffRole);

        //Subjects
        Subject math = Subject.builder()
                .subjectName("Math")
                .build();

        Subject science = Subject.builder()
                .subjectName("Science")
                .build();

        Subject physics = Subject.builder()
                .subjectName("Physics")
                .build();

        Subject physicalEducation = Subject.builder()
                .subjectName("Physical Education")
                .build();

        Subject art = Subject.builder()
                .subjectName("Art")
                .build();

        List<Subject> subjects = List.of(math, science);
        List<Subject> leftSubjects = List.of(physics, physicalEducation, art);

        //People
        Student student = Student.builder()
                .firstName("Nuno")
                .lastName("Carmo")
                .birthDate(LocalDate.of(1995, 10, 15))
                .accountCreationDate(LocalDate.now())
                .email("nuno@email.com")
                .password(this.passwordEncoder.encode("nunocarmo"))
                .roleId(studentRole)
                .studentId(1L)
                .subjectList(new HashSet<>(subjects))
                .build();

        Teacher teacher = Teacher.builder()
                .firstName("Christophe")
                .lastName("Soares")
                .birthDate(LocalDate.of(1982, 4, 12))
                .accountCreationDate(LocalDate.now())
                .email("chris@email.com")
                .password(this.passwordEncoder.encode("christophe"))
                .roleId(teacherRole)
                .teacherId(1L)
                .subjectList(new HashSet<>(subjects))
                .build();

        Staff staff = Staff.builder()
                .firstName("João")
                .lastName("Silva")
                .birthDate(LocalDate.of(1998, 8, 3))
                .accountCreationDate(LocalDate.now())
                .email("joao@email.com")
                .password(this.passwordEncoder.encode("joaosilva"))
                .roleId(staffRole)
                .staffId(1L)
                .build();

        //Grades
        Grade mathGrade = Grade.builder()
                .value(17)
                .studentId(student)
                .subjectId(math)
                .build();

        Grade scienceGrade = Grade.builder()
                .value(13)
                .studentId(student)
                .subjectId(science)
                .build();

        this.roleRepository.saveAll(roles);
        this.subjectRepository.saveAll(subjects);
        this.subjectRepository.saveAll(leftSubjects);

        this.studentRepository.save(student);
        this.teacherRepository.save(teacher);
        this.staffRepository.save(staff);

        this.gradeRepository.save(mathGrade);
        this.gradeRepository.save(scienceGrade);
    }
}
