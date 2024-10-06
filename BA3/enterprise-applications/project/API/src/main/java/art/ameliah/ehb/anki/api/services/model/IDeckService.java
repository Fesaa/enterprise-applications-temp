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

    void addTag(Deck deck, List<Tag> tags);
    void removeTag(Deck deck, List<Tag> tags);
    void setTags(Deck deck, List<Tag> tags);

    default void addTag(Deck deck, Tag... tag) {
        addTag(deck, List.of(tag));
    }

    default void removeTag(Deck deck, Tag... tag) {
        removeTag(deck, List.of(tag));
    }

    default void setTag(Deck deck, Tag... tag) {
        setTags(deck, List.of(tag));
    }

}
