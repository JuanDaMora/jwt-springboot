package judamov.
        sipoh.Jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import judamov.sipoh.exceptions.GenericAppException;
import judamov.sipoh.repository.IUserRepository;
import judamov.sipoh.service.impl.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtServiceImpl jwtServiceImpl;
    private final IUserRepository IUserRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
        final String username;
        String path = request.getRequestURI();
        if(path.startsWith("/api")){
            if (token == null)  {
                System.out.println("🔸 No token en la cabecera Authorization.");
                filterChain.doFilter(request, response);
                return;
            }

            username = jwtServiceImpl.getUsernameFromToken(token);
            System.out.println("🔸 Username extraído del token: " + username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails user = IUserRepository.findOneByDocumento(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

                System.out.println("🔸 Usuario encontrado: " + user.getUsername());

                if (!jwtServiceImpl.isTokenValid(token, user)) {
                    System.out.println("⛔ Token inválido o expirado.");
                    throw new GenericAppException(HttpStatus.UNAUTHORIZED, "Token inválido");
                }

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println("✅ Usuario autenticado y contexto de seguridad establecido.");
            }
        }

        filterChain.doFilter(request, response);
    }


    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
