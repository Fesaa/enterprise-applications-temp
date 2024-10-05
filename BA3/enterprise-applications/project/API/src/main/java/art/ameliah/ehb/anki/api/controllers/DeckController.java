package art.ameliah.ehb.anki.api.controllers;

import art.ameliah.ehb.anki.api.annotations.BaseController;
import art.ameliah.ehb.anki.api.dtos.deck.CreateDeckDto;
import art.ameliah.ehb.anki.api.dtos.deck.DeckDto;
import art.ameliah.ehb.anki.api.exceptions.UnAuthorized;
import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.deck.Deck;
import art.ameliah.ehb.anki.api.models.tags.Tag;
import art.ameliah.ehb.anki.api.services.model.IDeckService;
import art.ameliah.ehb.anki.api.services.model.ITagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@BaseController
@RequestMapping("/api/deck")
@RequiredArgsConstructor
public class DeckController {

    private final IDeckService deckService;
    private final ITagService tagService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<Deck> getDecks() {
        return deckService.getDecks(User.current());
    }

    @GetMapping("/{id}")
    public DeckDto getDeck(@PathVariable Long id) {
        Deck deck = deckService.getDeck(id).orElseThrow();
        if (!deck.isOwner(User.current()))
            throw new UnAuthorized();

        return modelMapper.map(deck, DeckDto.class);
    }

    @PostMapping
    public DeckDto createDeck(@RequestBody CreateDeckDto createDeckDto) {
        User user = User.current();
        Deck deck = Deck.builder()
                .title(createDeckDto.getTitle())
                .description(createDeckDto.getDescription())
                .tags(createDeckDto.getTags().stream().map(Tag::new).toList())
                .user(user)
                .build();

        return modelMapper.map(deckService.create(deck), DeckDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteDeck(@PathVariable Long id) {
        User user = User.current();
        Deck deck = deckService.getDeckLazy(id).orElseThrow();

        if (!deck.isOwner(user)) {
            throw new UnAuthorized();
        }

        deckService.delete(deck);
    }

    @PostMapping("/{id}/tag/{tagId}")
    public void addTag(@PathVariable Long id, @PathVariable Long tagId) {
        Deck deck = deckService.getDeckLazy(id).orElseThrow();
        if (!deck.isOwner(User.current())) {
            throw new UnAuthorized();
        }

        Tag tag = tagService.getTag(tagId).orElseThrow();
        if (!tag.isOwner(User.current())) {
            throw new UnAuthorized();
        }

        deckService.addTag(deck, tag);
    }

    @DeleteMapping("/{id}/tag/{tagId}")
    public void deleteTag(@PathVariable Long id, @PathVariable Long tagId) {
        Deck deck = deckService.getDeckLazy(id).orElseThrow();
        if (!deck.isOwner(User.current())) {
            throw new UnAuthorized();
        }

        Tag tag = tagService.getTag(tagId).orElseThrow();
        if (!tag.isOwner(User.current())) {
            throw new UnAuthorized();
        }

        deckService.removeTag(deck, tag);
    }

}
