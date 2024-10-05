package art.ameliah.ehb.anki.api.dtos.deck;

import art.ameliah.ehb.anki.api.models.deck.Card;

public class CardDto {

    Long id;

    Card.CardType type;

    Card.Difficulty difficulty;

    String question;

    String hint;

    String information;

}
