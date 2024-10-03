package art.ameliah.ehb.anki.api.controllers;

import art.ameliah.ehb.anki.api.annotations.BaseController;
import art.ameliah.ehb.anki.api.dtos.deck.CreateDeckDto;
import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.deck.Deck;
import art.ameliah.ehb.anki.api.models.tags.Tag;
import art.ameliah.ehb.anki.api.services.DeckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@BaseController
@RequestMapping("/deck")
@RequiredArgsConstructor
public class DeckController {

    private final DeckService deckService;

    @GetMapping
    public List<Deck> getDecks() {
        return deckService.getDecks(User.current());
    }

    @PostMapping
    public Deck createDeck(@RequestBody CreateDeckDto createDeckDto) {
        User user = User.current();
        Deck deck = Deck.builder()
                .title(createDeckDto.getTitle())
                .description(createDeckDto.getDescription())
                .tags(createDeckDto.getTags().stream().map(Tag::new).toList())
                .user(user)
                .build();
        return deckService.createDeck(deck);
    }

}
