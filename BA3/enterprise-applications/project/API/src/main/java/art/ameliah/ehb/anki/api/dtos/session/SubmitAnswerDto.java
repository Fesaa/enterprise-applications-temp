package art.ameliah.ehb.anki.api.dtos.session;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SubmitAnswerDto {

    Long cardId;
    String answer;

}
