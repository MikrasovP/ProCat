package proCat.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static io.jsonwebtoken.lang.Strings.hasText;

@Component
public class JwtFilter extends GenericFilterBean {
    private final JwtSupplier jwtSupplier;

    @Autowired
    public JwtFilter(JwtSupplier jwtSupplier) {
        this.jwtSupplier = jwtSupplier;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String actualToken = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (actualToken != null && jwtSupplier.isTokenValid(actualToken)) {
            Claims claims = jwtSupplier.getClaimsFromToken(actualToken);
            UserDetails customUserDetails = JwtUser.fromClaimsToCustomUserDetails(claims);
            Authentication authentication = new PreAuthenticatedAuthenticationToken(
                    customUserDetails,
                    null,
                    customUserDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }

    public String getTokenFromRequest(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
    public String getSubjectFromToken(HttpServletRequest httpServletRequest){
        return jwtSupplier.getClaimsFromToken(getTokenFromRequest(httpServletRequest)).getSubject();
    }

}
