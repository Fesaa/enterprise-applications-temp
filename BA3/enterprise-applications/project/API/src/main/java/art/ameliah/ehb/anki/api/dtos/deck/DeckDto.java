package art.ameliah.ehb.anki.api.dtos.deck;

import art.ameliah.ehb.anki.api.dtos.tags.TagDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DeckDto {

    Long id;

    String title;

    String description;

    List<CardDto> cards;

    List<TagDto> tags;

}
