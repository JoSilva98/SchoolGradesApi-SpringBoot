package school.schoolGrades.security;

public class JwtProperties {
    public static final String SECRET = "MY_SECRET";
    public static final int EXPIRATION_TIME = 600000; //600000 - 10 minutos, 86400000 - 10 dias
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
