package school.schoolGrades.command.extraTables;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GradeDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String subjectId;

    @Min(0)
    @Max(20)
    private int value;
}
