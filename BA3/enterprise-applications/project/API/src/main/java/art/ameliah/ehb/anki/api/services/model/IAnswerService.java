package art.ameliah.ehb.anki.api.services.model;

import art.ameliah.ehb.anki.api.dtos.deck.AnswerDto;
import art.ameliah.ehb.anki.api.models.deck.Answer;
import art.ameliah.ehb.anki.api.models.deck.Card;

import java.util.List;
import java.util.Optional;

public interface IAnswerService {

    void updateAnswers(Long cardId, List<AnswerDto> answerDtos);
    void removeAnswers(Long cardId, List<Long> answers);

    Optional<Answer> getAnswer(Card card, Long answerId);
    Optional<Answer> tryAnswer(Card card, String query);

 }
