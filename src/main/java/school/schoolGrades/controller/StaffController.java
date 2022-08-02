package school.schoolGrades.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import school.schoolGrades.command.PersonDto;
import school.schoolGrades.command.StudentDto;
import school.schoolGrades.command.TeacherDto;
import school.schoolGrades.command.extraTables.RoleDto;
import school.schoolGrades.command.extraTables.SubjectDto;
import school.schoolGrades.service.StaffServiceI;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/staff")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class StaffController {
    private final StaffServiceI staffService;

    @GetMapping("people")
    public List<PersonDto> getAllPeople() {
        return this.staffService.getAllPeople();
    }

    @GetMapping("people/{id}")
    public PersonDto getPeopleById(@PathVariable("id") Long id) {
        return this.staffService.getPeopleById(id);
    }

    @GetMapping("people/email")
    public List<PersonDto> getPeopleByEmail(@Valid @RequestBody String email) {
        return this.staffService.getPeopleByEmail(email);
    }

    @GetMapping("people/roleid/{id}")
    public List<PersonDto> getPeopleByRole(@PathVariable("id") int id) {
        return this.staffService.getPeopleByRole(id);
    }

    @GetMapping("students")
    public List<StudentDto> getAllStudents() {
        return this.staffService.getAllStudents();
    }

    @GetMapping("students/{id}")
    public StudentDto getStudentById(@PathVariable("id") Long id) {
        return this.staffService.getStudentById(id);
    }

    @GetMapping("teachers")
    public List<TeacherDto> getAllTeachers() {
        return this.staffService.getAllTeachers();
    }

    @GetMapping("teachers/{id}")
    public TeacherDto getTeacherById(@PathVariable("id") Long id) {
        return this.staffService.getTeacherById(id);
    }

    @GetMapping("subjects")
    public List<SubjectDto> getAllSubjects() {
        return this.staffService.getAllSubjects();
    }

    @GetMapping("subjects/{id}")
    public SubjectDto getSubjectById(@PathVariable("id") int id) {
        return this.staffService.getSubjectById(id);
    }

    @GetMapping("roles")
    public List<RoleDto> getAllRoles() {
        return this.staffService.getAllRoles();
    }

    @PostMapping("people")
    public PersonDto addPerson(@Valid @RequestBody PersonDto personDto) {
        return this.staffService.addPerson(personDto);
    }

    @PostMapping("subjects")
    public SubjectDto addSubject(@Valid @RequestBody SubjectDto subjectDto) {
        return this.staffService.addSubject(subjectDto);
    }

    @DeleteMapping("people/{id}")
    public void deletePerson(@PathVariable("id") Long id) {
        this.staffService.deletePerson(id);
    }

    @DeleteMapping("subjects/{id}")
    public void deleteSubject(@PathVariable("id") int id) {
        this.staffService.deleteSubject(id);
    }
}
