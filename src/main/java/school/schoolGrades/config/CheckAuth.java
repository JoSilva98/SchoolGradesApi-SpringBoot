package school.schoolGrades.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import school.schoolGrades.exception.ConflictException;
import school.schoolGrades.persistence.repository.PersonRepositoryI;

@Component
@RequiredArgsConstructor
public class CheckAuth {
    private final PersonRepositoryI personRepository;

    public void checkUserId(Long id) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .toString();

        this.personRepository.findByEmail(email)
                .ifPresent(x -> {
                    if (!x.getId().equals(id))
                        throw new ConflictException("This id is not yours");
                });
    }
}
