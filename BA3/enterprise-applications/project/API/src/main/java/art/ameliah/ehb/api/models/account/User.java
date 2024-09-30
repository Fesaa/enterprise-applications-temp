package art.ameliah.ehb.api.models.account;

import io.ebean.Model;
import io.ebean.annotation.JsonIgnore;
import io.ebean.annotation.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Getter
@Builder
@Entity
@Table(name = "\"user\"")
public class User extends Model implements UserDetails {

    @Id
    Long id;

    @NotNull
    String username;

    @NotNull
    String password; // REMOVE FROM JSON HELP

    @ManyToMany
    @JoinTable(
            name = "pivotuserrole",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> (GrantedAuthority) role::getName) // Return role names as authorities
                .toList();
    }
}
