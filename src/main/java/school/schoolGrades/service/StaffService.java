package school.schoolGrades.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.schoolGrades.command.PersonDto;
import school.schoolGrades.command.StaffDto;
import school.schoolGrades.command.StudentDto;
import school.schoolGrades.command.TeacherDto;
import school.schoolGrades.command.extraTables.RoleDto;
import school.schoolGrades.command.extraTables.SubjectDto;
import school.schoolGrades.config.CheckAuth;
import school.schoolGrades.converter.MainConverterI;
import school.schoolGrades.enums.RolesEnum;
import school.schoolGrades.exception.ConflictException;
import school.schoolGrades.exception.NotFoundException;
import school.schoolGrades.persistence.model.Person;
import school.schoolGrades.persistence.model.Staff;
import school.schoolGrades.persistence.model.Student;
import school.schoolGrades.persistence.model.Teacher;
import school.schoolGrades.persistence.model.extraTables.Role;
import school.schoolGrades.persistence.model.extraTables.Subject;
import school.schoolGrades.persistence.repository.PersonRepositoryI;
import school.schoolGrades.persistence.repository.StaffRepositoryI;
import school.schoolGrades.persistence.repository.StudentRepositoryI;
import school.schoolGrades.persistence.repository.TeacherRepositoryI;
import school.schoolGrades.persistence.repository.extraTables.RoleRepositoryI;
import school.schoolGrades.persistence.repository.extraTables.SubjectRepositoryI;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static school.schoolGrades.findBy.FindBy.*;

@Service
@RequiredArgsConstructor
public class StaffService implements StaffServiceI {
    private final PersonRepositoryI personRepository;
    private final StudentRepositoryI studentRepository;
    private final TeacherRepositoryI teacherRepository;
    private final StaffRepositoryI staffRepository;
    private final RoleRepositoryI roleRepository;
    private final SubjectRepositoryI subjectRepository;
    private final MainConverterI converter;
    private final PasswordEncoder encoder;
    private final CheckAuth checkAuth;

    @Override
    public List<PersonDto> getAllPeople() {
        return this.converter.listConverter(
                this.personRepository.findAll(), PersonDto.class
        );
    }

    @Override
    public PersonDto getPeopleById(Long id) {
        Person person = findPersonById(id, this.personRepository);
        return this.converter.converter(person, PersonDto.class);
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
    public List<StudentDto> getAllStudents() {
        return this.converter.listConverter(
                this.studentRepository.findAll(), StudentDto.class
        );
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = findStudentById(id, this.studentRepository);
        return this.converter.converter(student, StudentDto.class);
    }

    @Override
    public List<TeacherDto> getAllTeachers() {
        return this.converter.listConverter(
                this.teacherRepository.findAll(), TeacherDto.class
        );
    }

    @Override
    public TeacherDto getTeacherById(Long id) {
        Teacher teacher = findTeacherById(id, this.teacherRepository);
        return this.converter.converter(teacher, TeacherDto.class);
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
