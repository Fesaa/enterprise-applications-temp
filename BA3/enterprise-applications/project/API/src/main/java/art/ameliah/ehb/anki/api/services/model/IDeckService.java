package art.ameliah.ehb.anki.api.services.model;

import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.deck.Deck;

import java.util.List;
import java.util.Optional;

public interface IDeckService {

    Optional<Deck> getDeck(Long id);

    List<Deck> getDecks(User user);
    List<Deck> getDecks(Long userId);

    Deck createDeck(Deck deck);



}
