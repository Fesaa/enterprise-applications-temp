package art.ameliah.ehb.api.services;

import art.ameliah.ehb.api.exceptions.AppException;
import art.ameliah.ehb.api.models.account.Role;
import art.ameliah.ehb.api.models.account.User;
import art.ameliah.ehb.api.models.account.query.QUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secretKey;

    @Value("${security.jwt.expiration}")
    private long expiration;

    @PostConstruct
    public void init() {
        this.secretKey = Base64.getEncoder().encodeToString(this.secretKey.getBytes());
    }

    public String createToken(User user) {
        Date now = new Date();
        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("id", user.getId())
                .withClaim("roles", user.getRoles().stream().map(Role::getName).toList())
                .withIssuedAt(now)
                .sign(algorithm);
    }

    private AbstractAuthenticationToken validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        User user = User.builder()
                .id(decodedJWT.getClaim("id").asLong())
                .roles(decodedJWT.getClaim("roles")
                        .asList(String.class)
                        .stream()
                        .map(Role::of)
                        .toList())
                .build();

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    private AbstractAuthenticationToken validateTokenStrongly(String token) {
        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        User user = new QUser().id.eq(decodedJWT.getClaim("id").asLong())
                .findOneOrEmpty()
                .orElseThrow(() -> new AppException("Invalid token", HttpStatus.UNAUTHORIZED));

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    public Authentication validate(HttpServletRequest request, String token) {
        AbstractAuthenticationToken auth;

        if (request.getMethod().equals("GET")) {
            auth = validateToken(token);
        } else {
            auth = validateTokenStrongly(token);
        }

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return auth;
    }

}
