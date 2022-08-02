package school.schoolGrades.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import school.schoolGrades.exception.NotFoundException;
import school.schoolGrades.persistence.model.Person;
import school.schoolGrades.persistence.repository.PersonRepositoryI;

@Service
@RequiredArgsConstructor
public class PersonServiceDetails implements UserDetailsService {
    private final PersonRepositoryI personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = this.personRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("Person not found"));
        return PersonAuth.builder()
                .person(person)
                .build();
    }
}
