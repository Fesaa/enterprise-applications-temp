package art.ameliah.ehb.anki.api.dtos.tags;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTagDto {

    private String name;

    private String hexColour;
}
