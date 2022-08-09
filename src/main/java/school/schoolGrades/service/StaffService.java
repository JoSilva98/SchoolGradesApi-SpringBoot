package school.schoolGrades.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.schoolGrades.command.*;
import school.schoolGrades.command.extraTables.RoleDto;
import school.schoolGrades.command.extraTables.SubjectDto;
import school.schoolGrades.converter.MainConverterI;
import school.schoolGrades.enums.RolesEnum;
import school.schoolGrades.enums.TableEnum;
import school.schoolGrades.exception.ConflictException;
import school.schoolGrades.exception.NotFoundException;
import school.schoolGrades.persistence.model.Person;
import school.schoolGrades.persistence.model.Staff;
import school.schoolGrades.persistence.model.Student;
import school.schoolGrades.persistence.model.Teacher;
import school.schoolGrades.persistence.model.extraTables.Grade;
import school.schoolGrades.persistence.model.extraTables.Role;
import school.schoolGrades.persistence.model.extraTables.Subject;
import school.schoolGrades.persistence.repository.*;
import school.schoolGrades.persistence.repository.extraTables.GradeRepositoryI;
import school.schoolGrades.persistence.repository.extraTables.RoleRepositoryI;
import school.schoolGrades.persistence.repository.extraTables.SubjectRepositoryI;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static school.schoolGrades.helpers.FindBy.*;
import static school.schoolGrades.helpers.ValidateRequest.validatePages;

@Service
@RequiredArgsConstructor
public class StaffService implements StaffServiceI {
    private final PersonRepositoryI personRepository;
    private final StudentRepositoryI studentRepository;
    private final TeacherRepositoryI teacherRepository;
    private final StaffRepositoryI staffRepository;
    private final SubjectRepositoryI subjectRepository;
    private final GradeRepositoryI gradeRepository;
    private final RoleRepositoryI roleRepository;
    private final MainConverterI converter;
    private final PasswordEncoder encoder;

    @Override
    public List<PersonDto> getAllPeople(String table, String field, int page, int pageSize) {
        validatePages(page, pageSize);

        return switch (table.toUpperCase()) {
            case TableEnum.PEOPLE -> findAllPeople(field, page, pageSize);
            case TableEnum.STUDENTS -> findAllStudents(field, page, pageSize);
            case TableEnum.TEACHERS -> findAllTeachers(field, page, pageSize);
            case TableEnum.STAFF -> findAllStaff(field, page, pageSize);
            default -> throw new NotFoundException("Table not found");
        };
    }

    private List<PersonDto> findAllPeople(String field, int page, int pageSize) {
        List<Person> people = this.personRepository.findAll(
                PageRequest.of(page - 1, pageSize).withSort(Sort.by(field))
        ).stream().toList();

        return this.converter.listConverter(people, PersonDto.class);
    }

    private List<PersonDto> findAllStudents(String field, int page, int pageSize) {
        List<Student> students = this.studentRepository.findAll(
                PageRequest.of(page - 1, pageSize).withSort(Sort.by(field))
        ).stream().toList();

        return this.converter.listConverter(students, PersonDto.class);
    }

    private List<PersonDto> findAllTeachers(String field, int page, int pageSize) {
        List<Teacher> teachers = this.teacherRepository.findAll(
                PageRequest.of(page - 1, pageSize).withSort(Sort.by(field))
        ).stream().toList();

        return this.converter.listConverter(teachers, PersonDto.class);
    }

    private List<PersonDto> findAllStaff(String field, int page, int pageSize) {
        List<Staff> staff = this.staffRepository.findAll(
                PageRequest.of(page - 1, pageSize).withSort(Sort.by(field))
        ).stream().toList();

        return this.converter.listConverter(staff, PersonDto.class);
    }

    @Override
    public PersonDto getPeopleById(String table, Long id) {
        return switch (table.toUpperCase()) {
            case TableEnum.PEOPLE -> this.converter.converter(
                    findPersonById(id, this.personRepository), PersonDto.class);
            case TableEnum.STUDENTS -> this.converter.converter(
                    findStudentById(id, this.studentRepository), StudentDto.class);
            case TableEnum.TEACHERS -> this.converter.converter(
                    findTeacherById(id, this.teacherRepository), TeacherDto.class);
            case TableEnum.STAFF -> this.converter.converter(
                    findStaffById(id, this.staffRepository), StaffDto.class);
            default -> throw new NotFoundException("Table not found");
        };
    }


    @Override
    public List<PersonDto> getPeopleByEmail(String email) {
        List<Student> students = this.studentRepository.findLikeEmail(email);
        List<Teacher> teachers = this.teacherRepository.findLikeEmail(email);
        List<Staff> staff = this.staffRepository.findLikeEmail(email);

        List<Person> people = new ArrayList<>();
        Stream.of(students, teachers, staff).forEach(people::addAll);

        return this.converter.listConverter(people, PersonDto.class);
    }

    @Override
    public List<PersonDto> getPeopleByRole(int id) {
        Role role = findRoleById(id, this.roleRepository);
        int roleId = role.getId();

        List<Student> students = this.studentRepository.findByRoleId(roleId);
        List<Teacher> teachers = this.teacherRepository.findByRoleId(roleId);
        List<Staff> staff = this.staffRepository.findByRoleId(roleId);

        List<Person> people = new ArrayList<>();
        Stream.of(students, teachers, staff).forEach(people::addAll);

        return this.converter.listConverter(people, PersonDto.class);
    }

    @Override
    public List<PersonDto> getPeopleBySubject(String table, int id) {
        Subject subject = findSubjectById(id, this.subjectRepository);

        return switch (table.toUpperCase()) {
            case TableEnum.STUDENTS -> this.converter.listConverter(
                    subject.getStudentList(), PersonDto.class);
            case TableEnum.TEACHERS -> this.converter.listConverter(
                    subject.getTeacherList(), PersonDto.class);
            default -> throw new NotFoundException("Table not found");
        };
    }

    @Override
    public List<SubjectDto> getPersonSubjects(String table, Long id) {
        switch (table.toUpperCase()) {
            case TableEnum.STUDENTS -> {
                Student student = findStudentById(id, this.studentRepository);

                return this.converter.listConverter(
                        student.getSubjectList().stream().toList(), SubjectDto.class);
            }
            case TableEnum.TEACHERS -> {
                Teacher teacher = findTeacherById(id, this.teacherRepository);

                return this.converter.listConverter(
                        teacher.getSubjectList().stream().toList(), SubjectDto.class);
            }
            default -> throw new NotFoundException("Table not found");
        }
    }

    @Override
    public List<SubjectDto> getAllSubjects() {
        return this.converter.listConverter(
                this.subjectRepository.findAll(), SubjectDto.class
        );
    }

    @Override
    public SubjectDto getSubjectById(int id) {
        Subject subject = findSubjectById(id, this.subjectRepository);
        return this.converter.converter(subject, SubjectDto.class);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return this.converter.listConverter(
                this.roleRepository.findAll(), RoleDto.class
        );
    }

    @Override
    public PersonDto addPerson(PersonDto personDto) {
        this.personRepository.findByEmail(personDto.getEmail())
                .ifPresent(x -> {
                    throw new ConflictException("Email already exists");
                });

        Role role = this.roleRepository.findById(personDto.getRoleId())
                .orElseThrow(() -> new NotFoundException("Role not found"));

        personDto.setPassword(this.encoder.encode(personDto.getPassword()));

        return switch (role.getId()) {
            case RolesEnum.STUDENT -> saveStudent(personDto);
            case RolesEnum.TEACHER -> saveTeacher(personDto);
            default -> saveStaff(personDto);
        };
    }

    @Override
    public SubjectDto addSubject(SubjectDto subjectDto) {
        this.subjectRepository
                .findBySubjectName(subjectDto.getSubjectName())
                .ifPresent(x -> {
                    throw new ConflictException("Subject already exists");
                });

        Subject subject = this.subjectRepository.save(
                this.converter.converter(subjectDto, Subject.class)
        );

        return this.converter.converter(subject, SubjectDto.class);
    }

    @Override
    public PersonDto updatePerson(Long id, PersonUpdateDto updateDto) {
        Person person = findPersonById(id, this.personRepository);

        String newPass = updateDto.getPassword();
        if (newPass != null)
            updateDto.setPassword(this.encoder.encode(newPass));

        Person updatedPerson = this.converter.updateConverter(updateDto, person);

        return this.converter.converter(
                this.personRepository.save(updatedPerson), PersonDto.class);
    }

    @Override
    public List<PersonDto> joinPersonToSubject(String table, Long id, int subjectId) {
        Subject subject = findSubjectById(subjectId, this.subjectRepository);

        return switch (table.toUpperCase()) {
            case TableEnum.STUDENTS -> joinStudent(id, subject);
            case TableEnum.TEACHERS -> joinTeacher(id, subject);
            default -> throw new NotFoundException("Table not found");
        };
    }

    private List<PersonDto> joinStudent(Long id, Subject subject) {
        Student student = findStudentById(id, this.studentRepository);

        if (!student.addSubject(subject))
            throw new ConflictException("Student is already joining this subject");

        this.gradeRepository.save(
                Grade.builder()
                        .studentId(student)
                        .subjectId(subject)
                        .build());

        this.studentRepository.save(student);

        return this.converter.listConverter(
                subject.getStudentList(), PersonDto.class
        );
    }

    private List<PersonDto> joinTeacher(Long id, Subject subject) {
        Teacher teacher = findTeacherById(id, this.teacherRepository);

        if (!teacher.addSubject(subject))
            throw new ConflictException("Teacher is already teaching this subject");

        this.teacherRepository.save(teacher);

        return this.converter.listConverter(
                subject.getTeacherList(), PersonDto.class
        );
    }

    @Override
    public List<PersonDto> unjoinPersonFromSubject(String table, Long id, int subjectId) {
        Subject subject = findSubjectById(subjectId, this.subjectRepository);

        return switch (table.toUpperCase()) {
            case TableEnum.STUDENTS -> unjoinStudent(id, subject);
            case TableEnum.TEACHERS -> unjoinTeacher(id, subject);
            default -> throw new NotFoundException("Table not found");
        };
    }

    private List<PersonDto> unjoinStudent(Long id, Subject subject) {
        Student student = findStudentById(id, this.studentRepository);

        this.gradeRepository.delete(
                this.gradeRepository.findByStudentAndSubject(student.getId(), subject.getId())
                        .orElseThrow(() -> new NotFoundException("Grade not found"))
        );

        if (!student.removeSubject(subject))
            throw new ConflictException("Student is not joining this subject");

        this.studentRepository.save(student);

        return this.converter.listConverter(
                subject.getStudentList(), PersonDto.class
        );
    }

    private List<PersonDto> unjoinTeacher(Long id, Subject subject) {
        Teacher teacher = findTeacherById(id, this.teacherRepository);

        if (!teacher.removeSubject(subject))
            throw new ConflictException("Teacher is not teaching this subject");

        this.teacherRepository.save(teacher);

        return this.converter.listConverter(
                subject.getTeacherList(), PersonDto.class
        );
    }

    @Override
    public void deletePerson(Long id) {
        this.personRepository.delete(
                findPersonById(id, this.personRepository)
        );
    }

    @Override
    public void deleteSubject(int id) {
        findSubjectById(id, this.subjectRepository);
        this.subjectRepository.deleteFromStudents(id);
        this.subjectRepository.deleteFromTeachers(id);
        this.subjectRepository.deleteSubject(id);
    }

    private StudentDto saveStudent(PersonDto personDto) {
        Student student = this.converter.converter(
                personDto, Student.class
        );

        student.increaseStudentId();

        return this.converter.converter(
                this.studentRepository.save(student), StudentDto.class
        );
    }

    private TeacherDto saveTeacher(PersonDto personDto) {
        Teacher teacher = this.converter.converter(
                personDto, Teacher.class
        );

        teacher.increaseTeacherId();

        return this.converter.converter(
                this.teacherRepository.save(teacher), TeacherDto.class
        );
    }

    private StaffDto saveStaff(PersonDto personDto) {
        Staff staff = this.converter.converter(
                personDto, Staff.class
        );

        staff.increaseStaffId();

        return this.converter.converter(
                this.staffRepository.save(staff), StaffDto.class
        );
    }
}
