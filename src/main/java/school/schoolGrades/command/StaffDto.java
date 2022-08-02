package school.schoolGrades.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto extends PersonDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long staffId;
}
