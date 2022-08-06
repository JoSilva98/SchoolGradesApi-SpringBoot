package school.schoolGrades.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.schoolGrades.command.PersonDto;
import school.schoolGrades.command.PersonUpdateDto;
import school.schoolGrades.command.StudentDto;
import school.schoolGrades.command.TeacherDto;
import school.schoolGrades.command.extraTables.GradeDto;
import school.schoolGrades.command.extraTables.SubjectDto;
import school.schoolGrades.config.CheckAuth;
import school.schoolGrades.converter.MainConverterI;
import school.schoolGrades.exception.NotAllowedValueException;
import school.schoolGrades.exception.NotFoundException;
import school.schoolGrades.persistence.model.Student;
import school.schoolGrades.persistence.model.Teacher;
import school.schoolGrades.persistence.model.extraTables.Grade;
import school.schoolGrades.persistence.model.extraTables.Subject;
import school.schoolGrades.persistence.repository.StudentRepositoryI;
import school.schoolGrades.persistence.repository.TeacherRepositoryI;
import school.schoolGrades.persistence.repository.extraTables.SubjectRepositoryI;

import java.util.List;
import java.util.Set;

import static school.schoolGrades.helpers.FindBy.*;

@Service
@RequiredArgsConstructor
public class TeacherService implements TeacherServiceI {
    private final TeacherRepositoryI teacherRepository;
    private final SubjectRepositoryI subjectRepository;
    private final StudentRepositoryI studentRepository;
    private final MainConverterI converter;
    private final CheckAuth checkAuth;

    @Override
    public Set<SubjectDto> getTeacherSubjects(Long id) {
        this.checkAuth.checkUserId(id);

        Teacher teacher = findTeacherById(id, this.teacherRepository);
        return this.converter.converter(
                teacher, TeacherDto.class
        ).getSubjectList();
    }

    @Override
    public List<PersonDto> getStudentsBySubject(Long teacherId, int subjectId) {
        this.checkAuth.checkUserId(teacherId);

        Subject subject = findSubjectById(subjectId, this.subjectRepository);
        Teacher teacher = findTeacherById(teacherId, this.teacherRepository);

        checkIfTeacherTeachesTheSubject(teacher, subject);

        return this.converter.listConverter(
                subject.getStudentList(), PersonDto.class
        );
    }

    @Override
    public PersonDto updateTeacher(Long id, PersonUpdateDto personUpdateDto) {
        this.checkAuth.checkUserId(id);

        Teacher teacher = findTeacherById(id, this.teacherRepository);
        Teacher updatedTeacher = this.converter
                .updateConverter(personUpdateDto, teacher);
        return this.converter.converter(
                this.teacherRepository.save(updatedTeacher), PersonDto.class
        );
    }

    @Override
    public List<GradeDto> updateGrade(Long teacherId, Long studentId, int subjectId, int newValue) {
        this.checkAuth.checkUserId(teacherId);

        Teacher teacher = findTeacherById(teacherId, this.teacherRepository);
        Subject subject = findSubjectById(subjectId, this.subjectRepository);

        checkIfTeacherTeachesTheSubject(teacher, subject);

        Student student = findStudentById(studentId, this.studentRepository);

        Grade grade = student.getGradeList().stream()
                .filter(x -> x.getSubjectId().equals(subject))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Student is not joining this subject"));

        if (newValue < 1 || newValue > 20)
            throw new NotAllowedValueException("Value not allowed");

        grade.setValue(newValue);
        return this.converter.converter(
                        this.studentRepository.save(student), StudentDto.class)
                .getGradeList();
    }

    private void checkIfTeacherTeachesTheSubject(Teacher teacher, Subject subject) {
        if (!teacher.getSubjectList().contains(subject))
            throw new NotFoundException("You don't teach this subject");
    }
}
