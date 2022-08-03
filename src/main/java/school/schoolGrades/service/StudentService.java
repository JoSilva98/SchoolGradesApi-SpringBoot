package school.schoolGrades.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.schoolGrades.command.PersonDto;
import school.schoolGrades.command.PersonUpdateDto;
import school.schoolGrades.command.StudentDto;
import school.schoolGrades.command.extraTables.GradeDto;
import school.schoolGrades.command.extraTables.SubjectDto;
import school.schoolGrades.config.CheckAuth;
import school.schoolGrades.converter.MainConverterI;
import school.schoolGrades.enums.RolesEnum;
import school.schoolGrades.exception.ConflictException;
import school.schoolGrades.exception.NotFoundException;
import school.schoolGrades.persistence.model.Student;
import school.schoolGrades.persistence.model.extraTables.Grade;
import school.schoolGrades.persistence.model.extraTables.Subject;
import school.schoolGrades.persistence.repository.StudentRepositoryI;
import school.schoolGrades.persistence.repository.extraTables.GradeRepositoryI;
import school.schoolGrades.persistence.repository.extraTables.RoleRepositoryI;
import school.schoolGrades.persistence.repository.extraTables.SubjectRepositoryI;

import java.util.List;
import java.util.Set;

import static school.schoolGrades.Helpers.FindBy.findStudentById;
import static school.schoolGrades.Helpers.FindBy.findSubjectById;

@Service
@RequiredArgsConstructor
public class StudentService implements StudentServiceI {
    private final StudentRepositoryI studentRepository;
    private final SubjectRepositoryI subjectRepository;
    private final RoleRepositoryI roleRepository;
    private final GradeRepositoryI gradeRepository;
    private final MainConverterI converter;
    private final PasswordEncoder encoder;
    private final CheckAuth checkAuth;

    @Override
    public List<SubjectDto> getAllSubjects() {
        return this.converter.listConverter(
                this.subjectRepository.findAll(), SubjectDto.class
        );
    }

    @Override
    public List<GradeDto> getStudentGrades(Long id) {
        this.checkAuth.checkUserId(id);

        Student student = findStudentById(id, this.studentRepository);
        return this.converter
                .converter(student, StudentDto.class)
                .getGradeList();
    }

    @Override
    public Set<SubjectDto> getStudentSubjects(Long id) {
        this.checkAuth.checkUserId(id);

        Student student = findStudentById(id, this.studentRepository);
        return this.converter
                .converter(student, StudentDto.class)
                .getSubjectList();
    }

    @Override
    public StudentDto signUp(StudentDto studentDto) {
        this.studentRepository.findByEmail(studentDto.getEmail())
                .ifPresent(student -> {
                    throw new ConflictException("Email already exists");
                });

        Student student = this.converter.converter(studentDto, Student.class);

        student.setRoleId(
                this.roleRepository.findById(RolesEnum.STUDENT)
                        .orElseThrow(() -> new NotFoundException("Role not found")));

        student.setPassword(this.encoder.encode(student.getPassword()));
        student.increaseStudentId();

        return this.converter.converter(
                this.studentRepository.save(student), StudentDto.class
        );
    }

    @Override
    public PersonDto updateStudent(Long id, PersonUpdateDto personUpdateDto) {
        this.checkAuth.checkUserId(id);

        Student student = findStudentById(id, this.studentRepository);

        if (personUpdateDto.getPassword() != null)
            personUpdateDto.setPassword(
                    this.encoder.encode(personUpdateDto.getPassword())
            );

        Student updatedStudent = this.converter
                .updateConverter(personUpdateDto, student);

        return this.converter.converter(
                this.studentRepository.save(updatedStudent), PersonDto.class);
    }

    @Override
    public Set<SubjectDto> joinSubject(Long studentId, int subjectId) {
        this.checkAuth.checkUserId(studentId);

        Student student = findStudentById(studentId, this.studentRepository);
        Subject subject = findSubjectById(subjectId, this.subjectRepository);

        if (!student.addSubject(subject))
            throw new ConflictException("You've already joined this subject");

        this.gradeRepository.save(
                Grade.builder()
                        .studentId(student)
                        .subjectId(subject)
                        .build()
        );

        return this.converter.converter(
                this.studentRepository.save(student), StudentDto.class
        ).getSubjectList();
    }

    @Override
    public Set<SubjectDto> unjoinSubject(Long studentId, int subjectId) {
        this.checkAuth.checkUserId(studentId);

        Student student = findStudentById(studentId, this.studentRepository);
        Subject subject = findSubjectById(subjectId, this.subjectRepository);

        this.gradeRepository.delete(
                this.gradeRepository.findByStudentAndSubject(student.getId(), subject.getId())
                        .orElseThrow(() -> new NotFoundException("Grade not found"))
        );

        if (!student.removeSubject(subject))
            throw new ConflictException("You're not joining this subject");

        return this.converter.converter(
                this.studentRepository.save(student), StudentDto.class
        ).getSubjectList();
    }
}
