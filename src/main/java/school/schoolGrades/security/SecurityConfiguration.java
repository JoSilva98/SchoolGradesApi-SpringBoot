package school.schoolGrades.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import school.schoolGrades.persistence.repository.PersonRepositoryI;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PersonServiceDetails personService;
    private final PersonRepositoryI personRepository;

    public SecurityConfiguration(PersonServiceDetails personService,
                                 PersonRepositoryI personRepository) {
        this.personService = personService;
        this.personRepository = personRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Remove csrf and state in session
        http.csrf().disable() //Permite usar outros métodos para além do GET (csrf útil em form based authentication, em jwt não é útil)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Não é útil em jwt, mas sim em form based
                .and()
                //Add Jwt filters (1. authentication, 2. authorization)
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) //A ordem dos filtros importa
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.personRepository))
                //Configure access rules
                .authorizeRequests()
                //.anyRequest().authenticated() //Autoriza todos os requests apenas de users autenticados
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/student").permitAll()
                .antMatchers("/api/v1/student/**").hasRole("STUDENT")
                .antMatchers("/api/v1/teacher/**").hasRole("TEACHER")
                .antMatchers("/api/v1/staff/**").hasRole("STAFF");
        //.httpBasic(); ???
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.personService);

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() { //Para encriptar passwords
        return new BCryptPasswordEncoder();
    }
}
