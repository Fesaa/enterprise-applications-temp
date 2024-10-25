package art.ameliah.ehb.anki.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionDto {

    String code;
    String originalError;
    String message;

}
