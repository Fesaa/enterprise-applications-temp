package art.ameliah.ehb.api.models.account;

import io.ebean.annotation.NotNull;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
public class Role {

    @Id
    Long id;

    @NotNull
    String name;

    public static Role of(String name) {
        Role role = new Role();
        role.name = name;
        return role;
    }

}
