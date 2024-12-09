package art.ameliah.ehb.anki.api.services;

import art.ameliah.ehb.anki.api.models.deck.Card;
import art.ameliah.ehb.anki.api.models.deck.Deck;
import art.ameliah.ehb.anki.api.models.deck.query.QCard;
import art.ameliah.ehb.anki.api.services.model.ICardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardService implements ICardService {
    @Override
    public Optional<Card> getCard(Long id) {
        return new QCard().id.eq(id).findOneOrEmpty();
    }

    @Override
    public List<Card> getCards(Deck deck) {
        return getCards(deck.getId());
    }

    @Override
    public List<Card> getCards(Long deckId) {
        return new QCard().deck.id.eq(deckId).findList();
    }

    @Override
    public Card create(Card card) {
        card.save();
        return card;
    }

    @Override
    public void delete(Card card) {
        card.delete();
    }
}
