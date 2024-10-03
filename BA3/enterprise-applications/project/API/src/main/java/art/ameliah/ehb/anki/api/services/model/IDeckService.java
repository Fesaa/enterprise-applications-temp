package art.ameliah.ehb.anki.api.services.model;

import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.deck.Deck;
import art.ameliah.ehb.anki.api.models.tags.Tag;

import java.util.List;
import java.util.Optional;

public interface IDeckService {

    Optional<Deck> getDeck(Long id);
    Optional<Deck> getDeckLazy(Long id);

    List<Deck> getDecks(User user);
    List<Deck> getDecks(Long userId);

    Deck create(Deck deck);
    void delete(Deck deck);

    void addTag(Deck deck, Tag tag);
    void removeTag(Deck deck, Tag tag);



}
