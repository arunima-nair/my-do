package ae.dt.common.util;


import ae.dt.common.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JWTTokenUtil {

    public static final String HEADER_STRING = "Authorization";
    public static final String USER_NAME_TOKEN = "userNameToken";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "do_2019_12232323";

    @Autowired
    ApplicationPropertyLoader applicationPropertyLoader;


    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {

        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDTO userDTO) {
        return doGenerateToken(userDTO);
    }

    private String doGenerateToken(UserDTO userDTO) {

        Claims claims = Jwts.claims().setSubject(userDTO.getUserName());

//        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        claims.put("userName",userDTO.getUserName());
        claims.put("agentType",userDTO.getAgentType());
        claims.put("agentCode",userDTO.getAgentCode());
        claims.put("userType",userDTO.getUserType());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(applicationPropertyLoader.getStringProperty("issuer.url"))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public Boolean validateToken(String token, String userName) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userName) && !isTokenExpired(token));
    }
}