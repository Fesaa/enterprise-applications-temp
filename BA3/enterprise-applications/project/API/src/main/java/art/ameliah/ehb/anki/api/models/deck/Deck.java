package art.ameliah.ehb.anki.api.models.deck;

import art.ameliah.ehb.anki.api.models.Ownable;
import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.tags.Tag;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ebean.Model;
import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"user"})
public class Deck extends Model implements Ownable {

    public Deck(Long id) {
        this.id = id;
    }

    @Id
    Long id;

    @NotNull
    String title;

    String description;

    @ManyToOne(optional = false)
    User user;

    @OneToMany(cascade = CascadeType.ALL)
    List<Card> cards;

    @ManyToMany
    @JoinTable(
            name = "pivot_deck_tags",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    List<Tag> tags;

    @Override
    public User owner() {
        return user;
    }
}
