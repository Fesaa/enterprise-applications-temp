package art.ameliah.ehb.anki.api.dtos.deck;

import art.ameliah.ehb.anki.api.models.deck.Card;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
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

    public boolean isInValid() {
        if (this.question == null || this.question.isEmpty()) {
            return true;
        }

        if (this.information == null || this.information.isEmpty()) {
            return true;
        }

        if (this.answers.isEmpty()) {
            return true;
        }

        boolean hasCorrect = this.answers.stream().anyMatch(AnswerDto::getCorrect);
        if (!hasCorrect) {
            return true;
        }

        int max = this.type.equals(Card.CardType.STANDARD) ? 1 : 4;
        if (this.answers.size() > max) {
            return true;
        }

        return false;
    }

}
