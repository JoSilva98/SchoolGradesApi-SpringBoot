package school.schoolGrades.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import school.schoolGrades.command.PersonDto;
import school.schoolGrades.command.PersonUpdateDto;
import school.schoolGrades.command.StudentDto;
import school.schoolGrades.command.extraTables.GradeDto;
import school.schoolGrades.command.extraTables.SubjectDto;
import school.schoolGrades.service.StudentServiceI;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentServiceI studentService;

    @GetMapping("subjects")
    public List<SubjectDto> getAllSubjects() {
        return this.studentService.getAllSubjects();
    }

    @GetMapping("{id}/grades")
    public List<GradeDto> getStudentGrades(@PathVariable("id") Long id) {
        return this.studentService.getStudentGrades(id);
    }

    @GetMapping("{id}/subjects")
    public Set<SubjectDto> getStudentSubjects(@PathVariable("id") Long id) {
        return this.studentService.getStudentSubjects(id);
    }

    @PostMapping
    public StudentDto signUp(@Valid @RequestBody StudentDto studentDto) {
        return this.studentService.signUp(studentDto);
    }

    @PatchMapping("{id}")
    public PersonDto updateStudent(@PathVariable("id") Long id,
                                   @Valid @RequestBody PersonUpdateDto personUpdateDto) {
        return this.studentService.updateStudent(id, personUpdateDto);
    }

    @PatchMapping("join")
    public Set<SubjectDto> joinSubject(@RequestParam(value = "studentid") Long studentId,
                                       @RequestParam(value = "subjectid") int subjectId) {
        return this.studentService.joinSubject(studentId, subjectId);
    }

    @PatchMapping("unjoin")
    public Set<SubjectDto> unjoinSubject(@RequestParam(value = "studentid") Long studentId,
                                         @RequestParam(value = "subjectid") int subjectId) {
        return this.studentService.unjoinSubject(studentId, subjectId);
    }
}
