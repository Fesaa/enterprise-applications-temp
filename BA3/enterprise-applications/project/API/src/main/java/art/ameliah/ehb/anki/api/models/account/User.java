package art.ameliah.ehb.anki.api.models.account;

import art.ameliah.ehb.anki.api.exceptions.AppException;
import art.ameliah.ehb.anki.api.models.deck.Deck;
import art.ameliah.ehb.anki.api.models.tags.Tag;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ebean.Model;
import io.ebean.annotation.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.List;

@Getter
@Builder
@Entity
@Table(name = "\"user\"")
@JsonIgnoreProperties({"password"})
public class User extends Model implements UserDetails {

    @Id
    Long id;

    @NotNull
    String username;

    @NotNull
    String password; // REMOVE FROM JSON HELP

    @ManyToMany
    @JoinTable(
            name = "pivot_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    List<Role> roles;

    @OneToMany(mappedBy = "user")
    List<Deck> decks;

    @OneToMany(mappedBy = "user")
    List<Tag> tags;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> (GrantedAuthority) role::getName) // Return role names as authorities
                .toList();
    }

    public boolean isAdmin() {
        for (Role role : roles) {
            if (role.getName().equals("ADMIN")) {
                return true;
            }
        }
        return false;
    }

    public static User current() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null) {
            throw new AppException("Unauthorized");
        }

        return (User) principal;
    }
}
