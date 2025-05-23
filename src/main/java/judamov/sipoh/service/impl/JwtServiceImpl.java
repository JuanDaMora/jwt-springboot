package judamov.sipoh.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import judamov.sipoh.entity.User;
import judamov.sipoh.exceptions.GenericAppException;
import judamov.sipoh.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Jwts;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl {

    private static final String SECRET_KEY = "OASLK4N520LASNDASDPO342PASNVSDAAAS1123OASKFNVSA";
    private final IUserRepository IUserRepository;

    public String getToken(UserDetails userDetails) {
        User user = (User) userDetails;

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("documento", user.getDocumento());
        extraClaims.put("role", user.getRole().getName());
        extraClaims.put("userId", user.getId());

        return getToken(extraClaims, userDetails);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 horas
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] encodedKey = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(encodedKey);
    }

    public String getUsernameFromToken(String token) {
        return getAllClaims(token).get("documento", String.class);
    }
    public Integer getUserIdFromToken(String token) {
        return getAllClaims(token).get("userId", Integer.class);
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails user) {
        final String username = getUsernameFromToken(token);
        if (!username.equals(user.getUsername()) || isTokenExpired(token)) {
            throw new GenericAppException(HttpStatus.UNAUTHORIZED, "Token inválido o expirado");
        }

        User dbUser = IUserRepository.findOneByDocumento(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(user.getUsername()));

        String tokenHash = hashToken(token);

        if (dbUser.getTokenHash() != null) {
            if (!tokenHash.equals(dbUser.getTokenHash())) {
                throw new GenericAppException(HttpStatus.UNAUTHORIZED, "Token inválido");
            }
        }

        return true;
    }


    public String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generando hash del token", e);
        }
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public String getRoleFromToken(String token) {
        return getAllClaims(token).get("role", String.class);
    }
}
