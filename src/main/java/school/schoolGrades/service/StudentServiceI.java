package school.schoolGrades.service;

import school.schoolGrades.command.PersonDto;
import school.schoolGrades.command.PersonUpdateDto;
import school.schoolGrades.command.StudentDto;
import school.schoolGrades.command.extraTables.GradeDto;
import school.schoolGrades.command.extraTables.SubjectDto;

import java.util.List;
import java.util.Set;

public interface StudentServiceI {
    List<SubjectDto> getAllSubjects();

    List<GradeDto> getStudentGrades(Long id);

    Set<SubjectDto> getStudentSubjects(Long id);

    StudentDto signUp(StudentDto studentDto);

    PersonDto updateStudent(Long id, PersonUpdateDto personUpdateDto);

    Set<SubjectDto> joinSubject(Long studentId, int subjectId);

    Set<SubjectDto> unjoinSubject(Long studentId, int subjectId);
}
