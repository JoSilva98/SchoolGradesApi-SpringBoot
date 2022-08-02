package school.schoolGrades.service;

import school.schoolGrades.command.PersonDto;
import school.schoolGrades.command.PersonUpdateDto;
import school.schoolGrades.command.extraTables.GradeDto;
import school.schoolGrades.command.extraTables.SubjectDto;

import java.util.List;
import java.util.Set;

public interface TeacherServiceI {
    List<PersonDto> getStudentsBySubject(Long teacherId, int subjectId);

    List<GradeDto> updateGrade(Long teacherId, Long studentId, int subjectId, int newValue);

    PersonDto updateTeacher(Long id, PersonUpdateDto personUpdateDto);

    Set<SubjectDto> getTeacherSubjects(Long id);
}
