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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
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
