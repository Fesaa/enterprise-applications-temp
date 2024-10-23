package art.ameliah.ehb.anki.api.services.model;

import art.ameliah.ehb.anki.api.dtos.deck.AnswerDto;

import java.util.List;

public interface IAnswerService {

    void updateAnswers(Long cardId, List<AnswerDto> answerDtos);
    void removeAnswers(Long cardId, List<Long> answers);

 }
