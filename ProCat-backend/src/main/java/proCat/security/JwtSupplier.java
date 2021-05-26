package proCat.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtSupplier {
    private final Key key;
    private final JwtParser jwtParser;

    @Autowired
    public JwtSupplier(@Value("${jwtSecret}") String key) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build();
    }

    public String createTokenForUser(String data) {
        Date exDate = Date.from(
                LocalDate.now()
                        .plusMonths(1)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()
        );
        return Jwts.builder()
                .setExpiration(exDate)
                .setSubject(data)
                .signWith(key)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            jwtParser.parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }
}

