package art.ameliah.ehb.anki.api.dtos.deck;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AnswerDto {

    Long id;

    String answer;

    Boolean correct;

}
