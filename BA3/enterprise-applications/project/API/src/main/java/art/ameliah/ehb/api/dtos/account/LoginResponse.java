package art.ameliah.ehb.api.dtos.account;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    String username;
    String token;

}
