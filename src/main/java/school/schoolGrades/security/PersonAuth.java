package school.schoolGrades.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import school.schoolGrades.persistence.model.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PersonAuth implements UserDetails {
    private Person person;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        String role = "ROLE_" + this.person.getRoleId().getRoleName();
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        authorities.add(authority);
        /*Cria uma instância de SimpleGrantedAuthority com parâmetro "ROLE_(Role do usuário, por exemplo, STUDENT)
        e mete-o dentro da lista de authorities. Se cada usuário tivesse uma lista de permissões e/ou roles, faria um
        forEach(x -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + x); No caso de permissions, x, no caso de roles "ROLE_" + x
            authorities.add(authority);
        })"*/
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
