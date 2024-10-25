package art.ameliah.ehb.anki.api.services;

import art.ameliah.ehb.anki.api.dtos.deck.AnswerDto;
import art.ameliah.ehb.anki.api.models.deck.Answer;
import art.ameliah.ehb.anki.api.models.deck.Card;
import art.ameliah.ehb.anki.api.models.deck.query.QAnswer;
import art.ameliah.ehb.anki.api.models.deck.query.QCard;
import art.ameliah.ehb.anki.api.services.model.IAnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerService implements IAnswerService {


    @Override
    public void updateAnswers(Long cardId, List<AnswerDto> answerDtos) {
        Card card = new QCard().id.eq(cardId).findOneOrEmpty().orElseThrow();
        List<Answer> answers = answerDtos.stream().map(dto -> Answer.builder()
                        .card(card)
                        .answer(dto.getAnswer())
                        .correct(dto.getCorrect())
                        .build())
                .toList();
        card.getAnswers().addAll(answers);
        card.save();
    }

    @Override
    public void removeAnswers(Long cardId, List<Long> answers) {
        Card card = new QCard().id.eq(cardId).findOneOrEmpty().orElseThrow();
        card.getAnswers().removeIf(answer -> answers.contains(answer.getId()));
        card.save();
    }

    @Override
    public Optional<Answer> getAnswer(Card card, Long answerId) {
        return new QAnswer()
                .card.id.eq(card.getId())
                .id.eq(answerId)
                .findOneOrEmpty();
    }

    @Override
    public Optional<Answer> tryAnswer(Card card, String query) {
        return new QAnswer()
                .card.id.eq(card.getId())
                .answer.ieq(query)
                .findOneOrEmpty();
    }
}
