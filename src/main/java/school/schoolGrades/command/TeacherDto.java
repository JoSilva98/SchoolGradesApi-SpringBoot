package school.schoolGrades.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import school.schoolGrades.command.extraTables.SubjectDto;

import java.util.Set;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto extends PersonDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long teacherId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<SubjectDto> subjectList;
}
