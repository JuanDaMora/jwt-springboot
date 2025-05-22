package judamov.demo_jwt.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Transactional
@Entity
@Table(name="user", uniqueConstraints = {@UniqueConstraint(columnNames= {"documento"})})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer Id;
    @Column(unique=true)
    String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_document", nullable = false)
    private TypeDocument typeDocument;
    @Column(nullable = false)
    String documento;
    @Column(nullable = false)
    String password;
    @Column(nullable = false)
    String firstname;
    @Column(nullable = false)
    String lastname;
    @Column(nullable = false)
    Boolean active;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    private String tokenHash;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }
    @Override
    public String getUsername() {
        return documento;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
