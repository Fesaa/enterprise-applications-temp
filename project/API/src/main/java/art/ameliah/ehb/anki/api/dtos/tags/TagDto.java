package art.ameliah.ehb.anki.api.dtos.tags;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TagDto {

    Long id;

    String name;

    String hexColour;

    String normalizedName;

}
