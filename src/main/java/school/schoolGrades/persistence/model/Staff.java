package school.schoolGrades.persistence.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "staff")
public class Staff extends Person {
    private static Long staffIdStatic = 2L;

    @Column(nullable = false)
    private Long staffId = staffIdStatic;

    public void increaseStaffId() {
        staffIdStatic++;
    }
}
