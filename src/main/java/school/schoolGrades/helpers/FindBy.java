package school.schoolGrades.helpers;

import school.schoolGrades.exception.NotFoundException;
import school.schoolGrades.persistence.model.Person;
import school.schoolGrades.persistence.model.Student;
import school.schoolGrades.persistence.model.Teacher;
import school.schoolGrades.persistence.model.extraTables.Role;
import school.schoolGrades.persistence.model.extraTables.Subject;
import school.schoolGrades.persistence.repository.PersonRepositoryI;
import school.schoolGrades.persistence.repository.StudentRepositoryI;
import school.schoolGrades.persistence.repository.TeacherRepositoryI;
import school.schoolGrades.persistence.repository.extraTables.RoleRepositoryI;
import school.schoolGrades.persistence.repository.extraTables.SubjectRepositoryI;

public class FindBy {

    public static Person findPersonById(Long id, PersonRepositoryI personRepository) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person not found"));
    }

    public static Student findStudentById(Long id, StudentRepositoryI studentRepository) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found"));
    }

    public static Teacher findTeacherById(Long id, TeacherRepositoryI teacherRepository) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));
    }

    public static Subject findSubjectById(int id, SubjectRepositoryI subjectRepository) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subject not found"));
    }

    public static Role findRoleById(int id, RoleRepositoryI roleRepository) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found"));
    }
}
