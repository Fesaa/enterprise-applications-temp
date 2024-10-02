package art.ameliah.ehb.anki.api.dtos.account;

import art.ameliah.ehb.anki.api.models.account.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    User user;
    String token;

}
