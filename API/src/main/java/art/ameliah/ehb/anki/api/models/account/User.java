package art.ameliah.ehb.anki.api.models.account;

import art.ameliah.ehb.anki.api.exceptions.UnAuthorized;
import art.ameliah.ehb.anki.api.models.deck.Deck;
import art.ameliah.ehb.anki.api.models.tags.Tag;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ebean.Model;
import io.ebean.annotation.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
@Entity
@Table(name = "\"user\"")
@JsonIgnoreProperties({"password"})
@AllArgsConstructor
@NoArgsConstructor
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
        if (!(principal instanceof User)) {
            throw new UnAuthorized();
        }

        return (User) principal;
    }
}
