package art.ameliah.ehb.anki.api.services.model;

import art.ameliah.ehb.anki.api.models.deck.Card;
import art.ameliah.ehb.anki.api.models.deck.Deck;

import java.util.List;
import java.util.Optional;

public interface ICardService {

    Optional<Card> getCard(Long id);

    List<Card> getCards(Deck deck);
    List<Card> getCards(Long deckId);

    Card create(Card card);
    void delete(Card card);


}
