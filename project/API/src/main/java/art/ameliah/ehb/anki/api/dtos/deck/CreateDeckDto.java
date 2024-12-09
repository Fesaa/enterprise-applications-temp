package art.ameliah.ehb.anki.api.dtos.deck;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeckDto {

    String title;

    String description;

    List<Long> tags;

}
