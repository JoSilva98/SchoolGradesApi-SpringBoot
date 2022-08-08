package school.schoolGrades.service;

import school.schoolGrades.command.PersonDto;
import school.schoolGrades.command.StudentDto;
import school.schoolGrades.command.TeacherDto;
import school.schoolGrades.command.extraTables.RoleDto;
import school.schoolGrades.command.extraTables.SubjectDto;

import java.util.List;

public interface StaffServiceI {
    List<PersonDto> getAllPeople(String field, int page, int pageSize);

    PersonDto getPeopleById(Long id);

    List<PersonDto> getPeopleByEmail(String email);

    List<PersonDto> getPeopleByRole(int id);

    List<StudentDto> getAllStudents(String field, int page, int pageSize);

    StudentDto getStudentById(Long id);

    List<PersonDto> getStudentsBySubject(int id);

    List<TeacherDto> getAllTeachers(String field, int page, int pageSize);

    TeacherDto getTeacherById(Long id);

    List<SubjectDto> getAllSubjects();

    SubjectDto getSubjectById(int id);

    List<RoleDto> getAllRoles();

    PersonDto addPerson(PersonDto personDto);

    SubjectDto addSubject(SubjectDto subjectDto);

    List<PersonDto> joinStudentToSubject(Long studentId, int subjectId);

    List<PersonDto> unjoinStudentToSubject(Long studentId, int subjectId);

    void deletePerson(Long id);

    void deleteSubject(int id);
}
