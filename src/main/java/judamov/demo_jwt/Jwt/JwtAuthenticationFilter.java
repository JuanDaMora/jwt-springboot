package judamov.demo_jwt.Jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import judamov.demo_jwt.repository.IUserRepository;
import judamov.demo_jwt.service.impl.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {
    private final JwtServiceImpl jwtServiceImpl;
    private final IUserRepository IUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        final String token = getTokenFromRequest(request);
        final String username;

        if(token == null)
        {
            filterChain.doFilter(request,response);
            return;
        }
        username = jwtServiceImpl.getUsernameFromToken(token);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() ==null){
            UserDetails user= IUserRepository.findByDocumento(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
            if(jwtServiceImpl.isTokenValid(token,user))
            {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        user.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
    private String getTokenFromRequest(HttpServletRequest request)
    {
        final String authHeader= request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText( authHeader ) && authHeader.startsWith("Bearer "))
        {
            return authHeader.substring(7);
        }
        return null;
    }

}
