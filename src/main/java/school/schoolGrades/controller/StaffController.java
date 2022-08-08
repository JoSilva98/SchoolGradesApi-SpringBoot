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
    public List<PersonDto> getAllPeople(@RequestParam(value = "field") String field,
                                        @RequestParam(value = "page") int page,
                                        @RequestParam(value = "pagesize") int pageSize) {
        return this.staffService.getAllPeople(field, page, pageSize);
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
    public List<StudentDto> getAllStudents(@RequestParam(value = "field") String field,
                                           @RequestParam(value = "page") int page,
                                           @RequestParam(value = "pagesize") int pageSize) {
        return this.staffService.getAllStudents(field, page, pageSize);
    }

    @GetMapping("students/{id}")
    public StudentDto getStudentById(@PathVariable("id") Long id) {
        return this.staffService.getStudentById(id);
    }

    @GetMapping("students/subject/{id}")
    public List<PersonDto> getStudentsBySubject(@PathVariable("id") int id) {
        return this.staffService.getStudentsBySubject(id);
    }

    @GetMapping("teachers")
    public List<TeacherDto> getAllTeachers(@RequestParam(value = "field") String field,
                                           @RequestParam(value = "page") int page,
                                           @RequestParam(value = "pagesize") int pageSize) {
        return this.staffService.getAllTeachers(field, page, pageSize);
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

    @PatchMapping("join")
    public List<PersonDto> joinStudentToSubject(@RequestParam(value = "studentid") Long studentId,
                                                @RequestParam(value = "subjectid") int subjectId) {
        return this.staffService.joinStudentToSubject(studentId, subjectId);
    }

    @PatchMapping("unjoin")
    public List<PersonDto> unjoinStudentToSubject(@RequestParam(value = "studentid") Long studentId,
                                                  @RequestParam(value = "subjectid") int subjectId) {
        return this.staffService.unjoinStudentToSubject(studentId, subjectId);
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
