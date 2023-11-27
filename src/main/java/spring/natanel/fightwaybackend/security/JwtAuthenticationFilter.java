package spring.natanel.fightwaybackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, //the whole JWT secretKey
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        var token = getJWTFromRequest(request);

        // If a valid token is present, validate it and set the authentication in the SecurityContext
        if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {
            var username = jwtProvider.getUsernameFromToken(token);  //getUsername from JWT Token
            var user = userDetailsService.loadUserByUsername(username); //get the whole user from repo with the username we just got from the JWT Token
            var authentication = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), null, user.getAuthorities());

            // Set the user as authenticated in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Get the token from the Authorization header.
     *
     * @param request with Authorization: Bearer token
     * @return The token from the Authorization Header
     */

    //Bearer eyJhbGciOiJIUzUxMiJ9.5ODAOTQ5MTB9.AWAn5Bl2DQ ... func that removes the "Bearer" to get the JWT request string
    private String getJWTFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {
            return authHeader.substring("Bearer ".length());
        }
        return null;
    }
}

