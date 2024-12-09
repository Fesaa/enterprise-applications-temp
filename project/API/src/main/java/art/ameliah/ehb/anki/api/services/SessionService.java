package art.ameliah.ehb.anki.api.services;

import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.deck.Card;
import art.ameliah.ehb.anki.api.models.deck.Deck;
import art.ameliah.ehb.anki.api.models.session.Session;
import art.ameliah.ehb.anki.api.models.session.SessionAnswer;
import art.ameliah.ehb.anki.api.models.session.query.QSession;
import art.ameliah.ehb.anki.api.models.session.query.QSessionAnswer;
import art.ameliah.ehb.anki.api.services.model.ISessionService;
import io.ebean.DB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService implements ISessionService {

    @Override
    public List<Session> allSessions(User user) {
        return this.allSessions(user.getId());
    }

    @Override
    public List<Session> allSessions(Long userId) {
        return new QSession().user.id.eq(userId).findList();
    }

    @Override
    public List<Session> allSessions(Deck deck) {
        return new QSession().deck.id.eq(deck.getId()).findList();
    }

    @Override
    public List<Session> runningSessions(User user) {
        return this.runningSessions(user.getId());
    }

    @Override
    public List<Session> runningSessions(Long userId) {
        return new QSession()
                .user.id.eq(userId)
                .finish.isNull()
                .findList();
    }

    @Override
    public List<Session> runningSessions(Deck deck) {
        return new QSession()
                .deck.id.eq(deck.getId())
                .finish.isNull()
                .findList();
    }

    @Override
    public Optional<Session> getSession(User user, Deck deck) {
        return new QSession()
                .user.id.eq(user.getId())
                .deck.id.eq(deck.getId())
                .finish.isNull()
                .findOneOrEmpty();
    }

    @Override
    public Optional<Session> getSession(Long sessionId) {
        return new QSession().id.eq(sessionId).findOneOrEmpty();
    }

    @Override
    public Session createSession(User user, Deck deck) {
        Session session = new Session();
        session.setUser(user);
        session.setDeck(deck);
        session.setStart(new Timestamp(System.currentTimeMillis()));
        session.save();
        return session;
    }

    @Override
    public void finishSession(Session session) {
        session.setFinish(new Timestamp(System.currentTimeMillis()));
        session.save();
    }

    @Override
    public void addAnswer(Long sessionId, Long cardId, String answer, boolean correct) {
        SessionAnswer.builder()
                .session(DB.reference(Session.class, sessionId))
                .card(DB.reference(Card.class, cardId))
                .answer(answer)
                .correct(correct)
                .build().save();
    }
}
