package school.schoolGrades.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import school.schoolGrades.command.PersonDto;
import school.schoolGrades.command.PersonUpdateDto;
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

    @GetMapping("{table}")
    public List<PersonDto> getAllPeople(@PathVariable("table") String table,
                                        @RequestParam(value = "field") String field,
                                        @RequestParam(value = "page") int page,
                                        @RequestParam(value = "pagesize") int pageSize) {
        return this.staffService.getAllPeople(table, field, page, pageSize);
    }

    @GetMapping
    public PersonDto getPeopleById(@RequestParam(value = "table") String table,
                                   @RequestParam(value = "id") Long id) {
        return this.staffService.getPeopleById(table, id);
    }

    @GetMapping("people/email")
    public List<PersonDto> getPeopleByEmail(@Valid @RequestBody String email) {
        return this.staffService.getPeopleByEmail(email);
    }

    @GetMapping("people/roleid/{id}")
    public List<PersonDto> getPeopleByRole(@PathVariable("id") int id) {
        return this.staffService.getPeopleByRole(id);
    }

    @GetMapping("subjects/{id}/table/{table}")
    public List<PersonDto> getPeopleBySubject(@PathVariable("id") int id,
                                              @PathVariable("table") String table) {
        return this.staffService.getPeopleBySubject(table, id);
    }

    @GetMapping("{table}/id/{id}")
    public List<SubjectDto> getPersonSubjects(@PathVariable("table") String table,
                                              @PathVariable("id") Long id) {
        return this.staffService.getPersonSubjects(table, id);
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

    @PatchMapping("person/{id}")
    public PersonDto updatePerson(@PathVariable("id") Long id,
                                  @Valid @RequestBody PersonUpdateDto updateDto) {
        return this.staffService.updatePerson(id, updateDto);
    }

    @PatchMapping("join")
    public List<PersonDto> joinPersonToSubject(@RequestParam(value = "table") String table,
                                               @RequestParam(value = "id") Long id,
                                               @RequestParam(value = "subjectid") int subjectId) {
        return this.staffService.joinPersonToSubject(table, id, subjectId);
    }

    @PatchMapping("unjoin")
    public List<PersonDto> unjoinPersonFromSubject(@RequestParam(value = "table") String table,
                                                   @RequestParam(value = "id") Long id,
                                                   @RequestParam(value = "subjectid") int subjectId) {
        return this.staffService.unjoinPersonFromSubject(table, id, subjectId);
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
