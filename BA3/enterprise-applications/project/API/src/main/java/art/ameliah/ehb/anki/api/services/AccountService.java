package art.ameliah.ehb.anki.api.services;

import art.ameliah.ehb.anki.api.dtos.account.LoginDto;
import art.ameliah.ehb.anki.api.dtos.account.RegisterDto;
import art.ameliah.ehb.anki.api.exceptions.AppException;
import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.account.query.QUser;
import art.ameliah.ehb.anki.api.services.model.IAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@Slf4j
@Service
@AllArgsConstructor
public class AccountService implements IAccountService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User login(LoginDto loginDto) {
        User user = new QUser()
                .username.equalTo(loginDto.getUsername())
                .decks.fetch()
                .roles.fetch()
                .findOneOrEmpty()
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(loginDto.getPassword()), user.getPassword())) {
            return user;
        }

        throw new AppException("Invalid password", HttpStatus.UNAUTHORIZED);
    }

    @Override
    public User register(RegisterDto registerDto) {
        if (new QUser().username.equalTo(registerDto.getUsername()).findOneOrEmpty().isPresent()) {
            throw new AppException("User already exists", HttpStatus.CONFLICT);
        }

        User user = User.builder()
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(CharBuffer.wrap(registerDto.getPassword())))
                .build();
        user.save();
        return user;
    }

    @Override
    public User getUser(Long id) {
        return new QUser().id.equalTo(id).findOne();
    }

    @Override
    public User getUser(String username) {
        return new QUser().username.equalTo(username).findOne();
    }

}
