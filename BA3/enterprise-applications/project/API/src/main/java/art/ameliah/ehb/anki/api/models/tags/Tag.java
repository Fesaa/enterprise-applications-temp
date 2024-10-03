package art.ameliah.ehb.anki.api.models.tags;

import art.ameliah.ehb.anki.api.models.Ownable;
import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.deck.Deck;
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
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"user"})
public class Tag extends Model implements Ownable {

    public Tag(Long id) {
        this.id = id;
    }

    @Id
    Long id;

    @NotNull
    String name;

    String hexColour;

    @NotNull
    String normalizedName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "pivot_deck_tags",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    List<Deck> decks;

    @ManyToOne
    User user;

    @Override
    public User owner() {
        return user;
    }
}
