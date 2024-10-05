package art.ameliah.ehb.anki.api.controllers;

import art.ameliah.ehb.anki.api.annotations.BaseController;
import art.ameliah.ehb.anki.api.dtos.deck.CardDto;
import art.ameliah.ehb.anki.api.dtos.deck.CreateCardDto;
import art.ameliah.ehb.anki.api.exceptions.UnAuthorized;
import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.deck.Card;
import art.ameliah.ehb.anki.api.models.deck.Deck;
import art.ameliah.ehb.anki.api.services.model.ICardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@BaseController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final ICardService cardService;
    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public CardDto getCardById(@PathVariable Long id) {
        return modelMapper.map(cardService.getCard(id).orElseThrow(), CardDto.class);
    }

    @PostMapping("/{id}")
    public CardDto updateCard(@PathVariable Long id, @RequestBody CreateCardDto createCardDto) {
        Card card = cardService.getCard(id).orElseThrow();
        if (!card.getDeck().isOwner(User.current()))
            throw new UnAuthorized();

        if (createCardDto.getHint() != null)
            card.setHint(createCardDto.getHint());

        if (createCardDto.getType() != null)
            card.setType(createCardDto.getType());

        if (createCardDto.getInformation() != null)
            card.setInformation(createCardDto.getInformation());

        if (createCardDto.getDifficulty() != null)
            card.setDifficulty(createCardDto.getDifficulty());

        if (createCardDto.getQuestion() != null)
            card.setQuestion(createCardDto.getQuestion());


        card.save();
        return modelMapper.map(card, CardDto.class);
    }

    @PostMapping
    public CardDto createCard(@RequestBody CreateCardDto card) {
        Card c = cardService.create(Card.builder()
                .difficulty(card.getDifficulty())
                .hint(card.getHint())
                .type(card.getType())
                .information(card.getInformation())
                .question(card.getQuestion())
                .deck(new Deck(card.getDeck()))
                .build());

        return modelMapper.map(c, CardDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable Long id) {
        Card card = cardService.getCard(id).orElseThrow();
        if (!card.getDeck().isOwner(User.current()))
            throw new UnAuthorized();

        cardService.delete(card);
    }

}
