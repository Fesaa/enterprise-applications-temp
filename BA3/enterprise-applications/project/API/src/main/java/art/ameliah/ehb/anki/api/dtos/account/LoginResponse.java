package art.ameliah.ehb.anki.api.dtos.account;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    String username;
    String token;

}
