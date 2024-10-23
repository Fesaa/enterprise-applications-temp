package art.ameliah.ehb.anki.api.controllers;

import art.ameliah.ehb.anki.api.annotations.BaseController;
import art.ameliah.ehb.anki.api.dtos.deck.AnswerDto;
import art.ameliah.ehb.anki.api.services.model.IAnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@BaseController
@RequestMapping("/api/cards/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final IAnswerService answerService;

    @PostMapping("/add/{id}")
    public void addAnswer(@PathVariable("id") Long cardId, List<AnswerDto> answers) {
        this.answerService.updateAnswers(cardId, answers);
    }

    @PostMapping("/remove/{id}")
    public void removeAnswer(@PathVariable("id") Long cardId, List<Long> answers) {
        this.answerService.removeAnswers(cardId, answers);
    }
}
