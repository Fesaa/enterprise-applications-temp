package art.ameliah.ehb.anki.api.dtos.account;

import art.ameliah.ehb.anki.api.dtos.deck.DeckDto;
import art.ameliah.ehb.anki.api.dtos.tags.TagDto;
import art.ameliah.ehb.anki.api.models.account.Role;
import lombok.Setter;

import java.util.List;

public class UserDto {

    Long id;

    String username;

    List<Role> roles;

    List<DeckDto> decks;

    List<TagDto> tags;

    @Setter
    String token;



}
