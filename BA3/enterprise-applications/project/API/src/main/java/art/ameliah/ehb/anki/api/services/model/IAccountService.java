package art.ameliah.ehb.anki.api.services.model;

import art.ameliah.ehb.anki.api.dtos.account.LoginDto;
import art.ameliah.ehb.anki.api.dtos.account.RegisterDto;
import art.ameliah.ehb.anki.api.models.account.User;

public interface IAccountService {

    User login(LoginDto loginDto);

    User register(RegisterDto registerDto);

    User getUser(Long id);

    User getUser(String username);

}
