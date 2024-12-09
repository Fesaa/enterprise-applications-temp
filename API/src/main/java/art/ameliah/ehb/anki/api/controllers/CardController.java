package art.ameliah.ehb.anki.api.controllers;

import art.ameliah.ehb.anki.api.annotations.BaseController;
import art.ameliah.ehb.anki.api.dtos.deck.AnswerDto;
import art.ameliah.ehb.anki.api.dtos.deck.CardDto;
import art.ameliah.ehb.anki.api.exceptions.UnAuthorized;
import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.deck.Answer;
import art.ameliah.ehb.anki.api.models.deck.Card;
import art.ameliah.ehb.anki.api.models.deck.Deck;
import art.ameliah.ehb.anki.api.services.AnswerService;
import art.ameliah.ehb.anki.api.services.model.ICardService;
import io.ebean.DB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@BaseController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final ICardService cardService;
    private final AnswerService answerService;
    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public CardDto getCardById(@PathVariable Long id) {
        return modelMapper.map(cardService.getCard(id).orElseThrow(), CardDto.class);
    }

    @PostMapping("/{id}")
    public CardDto updateCard(@PathVariable Long id, @RequestBody CardDto dto) throws BadRequestException {
        if (dto.isInValid()) {
            throw new BadRequestException();
        }

        Card card = cardService.getCard(id).orElseThrow();
        if (!card.getDeck().isOwner(User.current()))
            throw new UnAuthorized();

        card.setHint(dto.getHint());
        card.setType(dto.getType());
        card.setInformation(dto.getInformation());
        card.setDifficulty(dto.getDifficulty());
        card.setQuestion(dto.getQuestion());
        card.save();
        this.answerService.updateAnswers(card.getId(), dto.getAnswers());
        return modelMapper.map(this.cardService.getCard(card.getId()).orElseThrow(), CardDto.class);
    }

    @PostMapping
    public CardDto createCard(@RequestBody CardDto card) throws BadRequestException {
        if (card.isInValid()) {
            throw new BadRequestException();
        }

        Card c = cardService.create(Card.builder()
                .difficulty(card.getDifficulty())
                .hint(card.getHint())
                .type(card.getType())
                .information(card.getInformation())
                .question(card.getQuestion())
                .deck(DB.reference(Deck.class, card.getDeckId()))
                .build());

        this.answerService.updateAnswers(c.getId(), card.getAnswers());
        return modelMapper.map(this.cardService.getCard(c.getId()).orElseThrow(), CardDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable Long id) {
        Card card = cardService.getCard(id).orElseThrow();
        if (!card.getDeck().isOwner(User.current()))
            throw new UnAuthorized();

        cardService.delete(card);
    }

}
