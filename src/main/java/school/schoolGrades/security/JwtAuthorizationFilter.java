package school.schoolGrades.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import school.schoolGrades.exception.NotFoundException;
import school.schoolGrades.persistence.model.Person;
import school.schoolGrades.persistence.repository.PersonRepositoryI;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final PersonRepositoryI personRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  PersonRepositoryI personRepository) {

        super(authenticationManager);
        this.personRepository = personRepository;
    }

    //Ativado sempre que houver um request para fazer a authorization
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        //Read the Authorization header, where the JWT token should be
        String header = request.getHeader(JwtProperties.HEADER_STRING);

        //If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        //If header is present, try grab user principal from database and perform authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Continue filter execution
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JwtProperties.HEADER_STRING);

        if (token != null) {
            //Parse the token and validate it
            String userName = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
                    .build()
                    .verify(token.replace(JwtProperties.TOKEN_PREFIX, ""))
                    .getSubject();

            //Search in the DB if we find the user by token subject (username)
            //If so, then grab user details and create Spring Auth Token using username, pass, authorities/roles
            if (userName != null) {
                Person person = this.personRepository.findByEmail(userName)
                        .orElseThrow(() -> new NotFoundException("Email not found"));

                PersonAuth principal = PersonAuth.builder()
                        .person(person)
                        .build();

                return new UsernamePasswordAuthenticationToken(userName,
                        null, principal.getAuthorities());
            }
            return null;
        }
        return null;
    }
}
