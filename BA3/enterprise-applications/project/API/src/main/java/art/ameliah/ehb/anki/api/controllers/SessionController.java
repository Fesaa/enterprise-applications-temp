package art.ameliah.ehb.anki.api.controllers;

import art.ameliah.ehb.anki.api.annotations.BaseController;
import art.ameliah.ehb.anki.api.dtos.session.SessionAnswerDto;
import art.ameliah.ehb.anki.api.dtos.session.SessionDto;
import art.ameliah.ehb.anki.api.exceptions.UnAuthorized;
import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.deck.Answer;
import art.ameliah.ehb.anki.api.models.deck.Card;
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
import java.util.NoSuchElementException;
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

    @GetMapping("/{sessionId}")
    public SessionDto getSession(@PathVariable Long sessionId) {
        User user = User.current();

        Session session = this.sessionService.getSession(sessionId).orElseThrow();
        session.assertOwner(user);

        return this.mapSession(session);
    }

    // TODO: See if we can make this on SQL
    private SessionDto mapSession(Session session) {
        SessionDto dto = modelMapper.map(session, SessionDto.class);
        dto.setCorrect(this.sessionService.correct(session));
        dto.setWrong(this.sessionService.incorrect(session));
        return dto;
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
    public boolean answerSession(@PathVariable Long sessionId, @RequestBody SessionAnswerDto dto) throws BadRequestException {
        log.info("{}, {}, {}, {}", dto.getId(), dto.getCardId(), dto.getAnswerId(), dto.getUserAnswer());
        User user = User.current();
        Session session = this.sessionService.getSession(sessionId).orElseThrow();
        if (!session.isOwner(user)) {
            throw new UnAuthorized();
        }

        if (session.getAnswers().stream().map(a -> a.getCard().getId()).toList().contains(dto.getCardId())) {
            log.debug("{} tried to userAnswer card {} again for session {}", user.getUsername(), dto.getCardId(), sessionId);
            throw new BadRequestException();
        }

        Card card = session.getDeck().getCards()
                .stream()
                .filter(c -> c.getId().equals(dto.getCardId()))
                .findFirst().orElseThrow();

        Answer answer = switch (card.getType()) {
            case MULTI -> this.answerService.getAnswer(card, dto.getAnswerId()).orElse(null);
            case STANDARD -> this.answerService.tryAnswer(card, dto.getUserAnswer()).orElse(null);
        };
        this.sessionService.addAnswer(sessionId, dto);
        if (answer == null) {
            return false;
        }
        return answer.getCorrect();
    }

}
