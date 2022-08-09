package school.schoolGrades.service;

import school.schoolGrades.command.PersonDto;
import school.schoolGrades.command.PersonUpdateDto;
import school.schoolGrades.command.extraTables.RoleDto;
import school.schoolGrades.command.extraTables.SubjectDto;

import java.util.List;

public interface StaffServiceI {
    List<PersonDto> getAllPeople(String table, String field, int page, int pageSize);

    PersonDto getPeopleById(String table, Long id);

    List<PersonDto> getPeopleByEmail(String email);

    List<PersonDto> getPeopleByRole(int id);

    List<PersonDto> getPeopleBySubject(String table, int id);

    List<SubjectDto> getPersonSubjects(String table, Long id);

    List<SubjectDto> getAllSubjects();

    SubjectDto getSubjectById(int id);

    List<RoleDto> getAllRoles();

    PersonDto addPerson(PersonDto personDto);

    SubjectDto addSubject(SubjectDto subjectDto);

    PersonDto updatePerson(Long id, PersonUpdateDto updateDto);

    List<PersonDto> joinPersonToSubject(String table, Long id, int subjectId);

    List<PersonDto> unjoinPersonFromSubject(String table, Long id, int subjectId);

    void deletePerson(Long id);

    void deleteSubject(int id);
}
