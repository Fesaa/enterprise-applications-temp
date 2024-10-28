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
    public CardDto updateCard(@PathVariable Long id, @RequestBody CardDto dto) {
        Card card = cardService.getCard(id).orElseThrow();
        if (!card.getDeck().isOwner(User.current()))
            throw new UnAuthorized();

        if (dto.getHint() != null)
            card.setHint(dto.getHint());

        if (dto.getType() != null)
            card.setType(dto.getType());

        if (dto.getInformation() != null)
            card.setInformation(dto.getInformation());

        if (dto.getDifficulty() != null)
            card.setDifficulty(dto.getDifficulty());

        if (dto.getQuestion() != null)
            card.setQuestion(dto.getQuestion());

        card.setAnswers(dto.getAnswers()
                .stream()
                .map(a -> Answer.builder()
                        .answer(a.getAnswer())
                        .correct(a.getCorrect())
                        .build())
                .toList());


        card.save();
        this.answerService.updateAnswers(card.getId(), dto.getAnswers());
        return modelMapper.map(card, CardDto.class);
    }

    @PostMapping
    public CardDto createCard(@RequestBody CardDto card) {
        Card c = cardService.create(Card.builder()
                .difficulty(card.getDifficulty())
                .hint(card.getHint())
                .type(card.getType())
                .information(card.getInformation())
                .question(card.getQuestion())
                .deck(DB.reference(Deck.class, card.getDeckId()))
                .build());

        this.answerService.updateAnswers(c.getId(), card.getAnswers());
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
