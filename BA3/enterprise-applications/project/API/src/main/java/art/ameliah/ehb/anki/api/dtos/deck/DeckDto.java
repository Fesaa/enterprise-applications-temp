package art.ameliah.ehb.anki.api.dtos.deck;

import art.ameliah.ehb.anki.api.dtos.tags.TagDto;

import java.util.List;

public class DeckDto {

    Long id;

    String title;

    String description;

    List<CardDto> cards;

    List<TagDto> tags;

}
