package art.ameliah.ehb.anki.api.services;

import art.ameliah.ehb.anki.api.dtos.deck.AnswerDto;
import art.ameliah.ehb.anki.api.models.deck.Answer;
import art.ameliah.ehb.anki.api.models.deck.Card;
import art.ameliah.ehb.anki.api.models.deck.query.QAnswer;
import art.ameliah.ehb.anki.api.models.deck.query.QCard;
import art.ameliah.ehb.anki.api.services.model.IAnswerService;
import io.ebean.DB;
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
        List<Answer> answers = answerDtos.stream().map(dto -> {
            Answer answer;
            if (dto.getId() != null && dto.getId() > 0) {
                answer = DB.reference(Answer.class, dto.getId());
            } else {
                answer = new Answer();
            }

            answer.setCard(card);
            answer.setAnswer(dto.getAnswer());
            answer.setCorrect(dto.getCorrect());
            answer.save();

            return answer;
        }).toList();
        card.setAnswers(answers);
        card.save();
    }

    @Override
    public void removeAnswers(Long cardId, List<Long> answers) {
        Card card = new QCard().id.eq(cardId).findOneOrEmpty().orElseThrow();
        card.getAnswers().removeIf(answer -> answers.contains(answer.getId()));
        card.save();
    }

    @Override
    public Optional<Answer> tryAnswer(Long cardId, String query) {
        return new QAnswer()
                .card.id.eq(cardId)
                .answer.ieq(query)
                .findOneOrEmpty();
    }
}
