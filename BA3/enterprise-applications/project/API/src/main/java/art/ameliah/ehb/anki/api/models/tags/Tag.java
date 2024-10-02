package art.ameliah.ehb.anki.api.models.tags;

import io.ebean.Model;
import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends Model {

    public Tag(Long id) {
        this.id = id;
    }

    @Id
    Long id;

    @NotNull
    String name;

    String hexColour;

    @NotNull
    String normalizedName;

}
