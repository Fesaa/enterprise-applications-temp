package art.ameliah.ehb.anki.api.dtos.deck;

import art.ameliah.ehb.anki.api.models.deck.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCardDto {

    Card.CardType type;
    Card.Difficulty difficulty;
    String question;
    String hint;
    String information;
    Long deck;
}
