package school.schoolGrades.service;

import school.schoolGrades.command.PersonDto;
import school.schoolGrades.command.StudentDto;
import school.schoolGrades.command.TeacherDto;
import school.schoolGrades.command.extraTables.RoleDto;
import school.schoolGrades.command.extraTables.SubjectDto;

import java.util.List;

public interface StaffServiceI {
    List<PersonDto> getAllPeople();

    PersonDto getPeopleById(Long id);

    List<PersonDto> getPeopleByEmail(String email);

    List<PersonDto> getPeopleByRole(int id);

    List<StudentDto> getAllStudents();

    StudentDto getStudentById(Long id);

    List<TeacherDto> getAllTeachers();

    TeacherDto getTeacherById(Long id);

    List<SubjectDto> getAllSubjects();

    SubjectDto getSubjectById(int id);

    List<RoleDto> getAllRoles();

    PersonDto addPerson(PersonDto personDto);

    SubjectDto addSubject(SubjectDto subjectDto);

    void deletePerson(Long id);

    void deleteSubject(int id);
}