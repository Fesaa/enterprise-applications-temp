package art.ameliah.ehb.anki.api.models.session;

import art.ameliah.ehb.anki.api.models.Ownable;
import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.deck.Deck;
import io.ebean.Model;
import io.ebean.annotation.NotNull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Entity
public class Session extends Model implements Ownable {

    @Id
    Long id;

    @NotNull
    Timestamp start;

    @Nullable
    Timestamp finish;

    @ManyToOne(optional = false)
    Deck deck;

    @ManyToOne(optional = false)
    User user;

    @OneToMany
    List<SessionAnswer> answers;

    @Override
    public User owner() {
        return this.user;
    }
}
