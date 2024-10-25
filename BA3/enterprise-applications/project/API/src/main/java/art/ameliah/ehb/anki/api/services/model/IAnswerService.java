package art.ameliah.ehb.anki.api.services.model;

import art.ameliah.ehb.anki.api.dtos.deck.AnswerDto;
import art.ameliah.ehb.anki.api.models.deck.Answer;

import java.util.List;
import java.util.Optional;

public interface IAnswerService {

    void updateAnswers(Long cardId, List<AnswerDto> answerDtos);
    void removeAnswers(Long cardId, List<Long> answers);

    Optional<Answer> tryAnswer(Long cardId, String query);

 }
