package art.ameliah.ehb.anki.api.services.model;

import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.deck.Deck;
import art.ameliah.ehb.anki.api.models.session.Session;

import java.util.List;
import java.util.Optional;

public interface ISessionService {

    List<Session> allSessions(User user);
    List<Session> allSessions(Long userId);
    List<Session> allSessions(Deck deck);

    List<Session> runningSessions(User user);
    List<Session> runningSessions(Long userId);
    List<Session> runningSessions(Deck deck);

    Optional<Session> getSession(User user, Deck deck);
    Optional<Session> getSession(Long sessionId);

    Session createSession(User user, Deck deck);
    void finishSession(Session session);

    void addAnswer(Long sessionId, Long cardId, String answer, boolean correct);

}
