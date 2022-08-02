package school.schoolGrades.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import school.schoolGrades.command.extraTables.GradeDto;
import school.schoolGrades.command.extraTables.SubjectDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto extends PersonDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long studentId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<SubjectDto> subjectList = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<GradeDto> gradeList = new ArrayList<>();
}
