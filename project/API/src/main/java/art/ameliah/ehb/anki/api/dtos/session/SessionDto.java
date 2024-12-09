package art.ameliah.ehb.anki.api.dtos.session;

import art.ameliah.ehb.anki.api.dtos.deck.DeckDto;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
public class SessionDto {

    Long id;

    Timestamp start;

    Timestamp finish;

    DeckDto deck;

    List<SessionAnswerDto> answers;

}
