package art.ameliah.ehb.anki.api.services;

import art.ameliah.ehb.anki.api.dtos.session.SessionAnswerDto;
import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.deck.Answer;
import art.ameliah.ehb.anki.api.models.deck.Card;
import art.ameliah.ehb.anki.api.models.deck.Deck;
import art.ameliah.ehb.anki.api.models.session.Session;
import art.ameliah.ehb.anki.api.models.session.SessionAnswer;
import art.ameliah.ehb.anki.api.models.session.query.QSession;
import art.ameliah.ehb.anki.api.models.session.query.QSessionAnswer;
import art.ameliah.ehb.anki.api.services.model.ISessionService;
import io.ebean.DB;
import io.ebean.RawSql;
import io.ebean.RawSqlBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService implements ISessionService {

    private final ModelMapper modelMapper;

    @Override
    public List<Session> allSessions(User user) {
        return this.allSessions(user.getId());
    }

    @Override
    public List<Session> allSessions(Long userId) {
        return new QSession().user.id.eq(userId).findList();
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
    public Integer correct(Session session) {
        return new QSessionAnswer()
                .session.id.eq(session.getId())
                .answer.correct.isTrue()
                .findCount();
    }

    @Override
    public Integer incorrect(Session session) {
        // UGHHH PLS
        return DB.sqlQuery("SELECT COUNT(*) as count FROM \"sessionAnswers\" s LEFT JOIN answer a ON a.id = s.answer_id WHERE session_id = :sessionId AND (a.correct OR answer_id IS NULL)")
                .setParameter("sessionId", session.getId())
                .findOneOrEmpty().orElseThrow().getInteger("count");

        // TODO: Investigate
        // This doesn't seem to generate a LEFT JOIN, idk how to make it work with these ebeans
        // Why are ORMs, so amazing, until they aren't huh...
        /*return new QSessionAnswer()
                .session.id.eq(session.getId())
                .or()
                    .answer.correct.isFalse()
                    .answer.isNull()
                .endOr()
                .findCount();*/
    }

    @Override
    public void addAnswer(Long sessionId, SessionAnswerDto answer) {
        Session session = this.getSession(sessionId).orElseThrow();

        SessionAnswer.SessionAnswerBuilder builder = SessionAnswer.builder()
                .session(session)
                .card(DB.reference(Card.class, answer.getCardId()))
                .userAnswer(answer.getUserAnswer());

        if (answer.getAnswerId() != null) {
            builder.answer(DB.reference(Answer.class, answer.getAnswerId()));
        }

        SessionAnswer sessionAnswer = builder.build();
        sessionAnswer.save();
    }
}
