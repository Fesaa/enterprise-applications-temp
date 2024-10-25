package art.ameliah.ehb.anki.api.models.session;

import art.ameliah.ehb.anki.api.models.deck.Answer;
import art.ameliah.ehb.anki.api.models.deck.Card;
import io.ebean.Model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "\"sessionAnswers\"")
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionAnswer extends Model {

    @Id
    Long id;

    @ManyToOne(optional = false)
    Session session;

    @ManyToOne(optional = false)
    Card card;

    @ManyToOne(optional = false)
    Answer answer;

    String userAnswer;

}
