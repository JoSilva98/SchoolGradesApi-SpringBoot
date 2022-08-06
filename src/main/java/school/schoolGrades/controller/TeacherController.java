package school.schoolGrades.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import school.schoolGrades.command.PersonDto;
import school.schoolGrades.command.PersonUpdateDto;
import school.schoolGrades.command.extraTables.GradeDto;
import school.schoolGrades.command.extraTables.SubjectDto;
import school.schoolGrades.service.TeacherServiceI;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/teacher")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class TeacherController {
    private final TeacherServiceI teacherService;

    @GetMapping("{id}/subjects")
    public Set<SubjectDto> getTeacherSubjects(@PathVariable("id") Long id) {
        return this.teacherService.getTeacherSubjects(id);
    }

    @GetMapping("students")
    public List<PersonDto> getStudentsBySubject(@RequestParam(value = "teacherid") Long teacherId,
                                                @RequestParam(value = "subjectid") int subjectId) {
        return this.teacherService.getStudentsBySubject(teacherId, subjectId);
    }

    @PatchMapping("{id}")
    public PersonDto updateTeacher(@PathVariable("id") Long id,
                                   @Valid @RequestBody PersonUpdateDto personUpdateDto) {
        return this.teacherService.updateTeacher(id, personUpdateDto);
    }

    @PatchMapping("grades")
    public List<GradeDto> updateGrade(@RequestParam(value = "teacherid") Long teacherId,
                                      @RequestParam(value = "studentid") Long studentId,
                                      @RequestParam(value = "subjectid") int subjectId,
                                      @Valid @RequestBody int newValue) {
        return this.teacherService.updateGrade(teacherId, studentId, subjectId, newValue);
    }
}
