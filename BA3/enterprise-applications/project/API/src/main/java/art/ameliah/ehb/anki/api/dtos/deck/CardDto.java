package art.ameliah.ehb.anki.api.dtos.deck;

import art.ameliah.ehb.anki.api.models.deck.Card;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CardDto {

    Long id;

    Long deckId;

    Card.CardType type;

    Card.Difficulty difficulty;

    String question;

    String hint;

    String information;

    List<AnswerDto> answers;

}
