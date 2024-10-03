package art.ameliah.ehb.anki.api.models.deck;

import io.ebean.Model;
import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card extends Model {

    @Id
    Long id;

    @NotNull
    @Enumerated
    CardType type;

    @NotNull
    @Enumerated
    Difficulty difficulty;

    @NotNull
    String question;

    @NotNull
    String hint;

    @NotNull
    String information;

    @ManyToOne
    Deck deck;


    public enum CardType {
        STANDARD,
        MULTI
    }

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD,
        IMPOSSIBLE
    }

}
