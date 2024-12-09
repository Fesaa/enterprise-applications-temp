package art.ameliah.ehb.anki.api.models.deck;

import io.ebean.Model;
import io.ebean.annotation.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Answer extends Model {

    @Id
    Long id;

    @ManyToOne
    Card card;

    @NotNull
    String answer;

    @NotNull
    Boolean correct = false;

}
