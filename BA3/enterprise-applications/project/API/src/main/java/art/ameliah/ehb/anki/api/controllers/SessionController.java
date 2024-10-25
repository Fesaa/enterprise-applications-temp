package art.ameliah.ehb.anki.api.controllers;

import art.ameliah.ehb.anki.api.annotations.BaseController;
import art.ameliah.ehb.anki.api.dtos.session.SessionDto;
import art.ameliah.ehb.anki.api.dtos.session.SubmitAnswerDto;
import art.ameliah.ehb.anki.api.exceptions.UnAuthorized;
import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.deck.Answer;
import art.ameliah.ehb.anki.api.models.deck.Deck;
import art.ameliah.ehb.anki.api.models.session.Session;
import art.ameliah.ehb.anki.api.services.model.IAccountService;
import art.ameliah.ehb.anki.api.services.model.IAnswerService;
import art.ameliah.ehb.anki.api.services.model.IDeckService;
import art.ameliah.ehb.anki.api.services.model.ISessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Slf4j
@BaseController
@RequestMapping("/api/session")
@RequiredArgsConstructor
public class SessionController {

    private final IDeckService deckService;
    private final ISessionService sessionService;
    private final IAnswerService answerService;
    private final IAccountService accountService;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    public List<SessionDto> getSessions() {
        User user = User.current();
        return this.sessionService.allSessions(user).stream()
                .map(this::mapSession)
                .toList();
    }

    @GetMapping("/running")
    public List<SessionDto> getRunningSessions() {
        User user = User.current();
        return this.sessionService.runningSessions(user).stream()
                .map(this::mapSession)
                .toList();
    }

    @GetMapping("/deck/{deckId}")
    public List<SessionDto> getDeckSessions(@PathVariable Long deckId) {
        User user = User.current();
        Deck deck = this.deckService.getDeckLazy(deckId).orElseThrow();
        deck.assertOwner(user);

        return this.sessionService.allSessions(deck).stream()
                .map(this::mapSession)
                .toList();
    }

    @GetMapping("/deck/{deckId}/running")
    public List<SessionDto> getRunningDeckSessions(@PathVariable Long deckId) {
        User user = User.current();
        Deck deck = this.deckService.getDeckLazy(deckId).orElseThrow();
        deck.assertOwner(user);

        return this.sessionService.runningSessions(deck).stream()
                .map(this::mapSession)
                .toList();
    }

    @GetMapping("/{sessionId}")
    public SessionDto getSession(@PathVariable Long sessionId) {
        User user = User.current();

        Session session = this.sessionService.getSession(sessionId).orElseThrow();
        session.assertOwner(user);

        return this.mapSession(session);
    }

    // TODO: See if we can make this on SQL
    private SessionDto mapSession(Session session) {
        return modelMapper.map(session, SessionDto.class);
    }

    @PostMapping("/{deckID}")
    public SessionDto createSession(@PathVariable Long deckID) {
        User user = User.current();
        Deck deck = this.deckService.getDeck(deckID).orElseThrow();
        deck.assertOwner(user);

        Optional<Session> existing = this.sessionService.getSession(user, deck);
        return existing.map(this::mapSession)
                .orElseGet(() -> this.modelMapper.map(this.sessionService.createSession(user, deck), SessionDto.class));

    }

    @PostMapping("/{sessionId}/finish")
    public void finishSession(@PathVariable Long sessionId) {
        User user = User.current();
        Session session = this.sessionService.getSession(sessionId).orElseThrow();
        if (!session.isOwner(user)) {
            throw new UnAuthorized();
        }

        this.sessionService.finishSession(session);
    }

    @PostMapping("/{sessionId}/answer")
    public boolean answerSession(@PathVariable Long sessionId, @RequestBody SubmitAnswerDto dto) throws BadRequestException {
        User user = User.current();
        Session session = this.sessionService.getSession(sessionId).orElseThrow();
        session.assertOwner(user);

        if (session.getAnswers().stream().map(a -> a.getCard().getId()).toList().contains(dto.getCardId())) {
            log.debug("{} tried to userAnswer card {} again for session {}", user.getUsername(), dto.getCardId(), sessionId);
            throw new BadRequestException();
        }

        Answer answer = this.answerService.tryAnswer(dto.getCardId(), dto.getAnswer()).orElse(null);
        boolean correct = answer != null && answer.getCorrect();
        this.sessionService.addAnswer(sessionId, dto.getCardId(), dto.getAnswer(), correct);

        return correct;
    }

}
