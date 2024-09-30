package art.ameliah.ehb.api.services;

import art.ameliah.ehb.api.dtos.account.LoginDto;
import art.ameliah.ehb.api.dtos.account.RegisterDto;
import art.ameliah.ehb.api.exceptions.AppException;
import art.ameliah.ehb.api.models.account.User;
import art.ameliah.ehb.api.models.account.query.QUser;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@Log
@Service
@AllArgsConstructor
public class AccountService {

    private final PasswordEncoder passwordEncoder;

    public User login(LoginDto loginDto) {
        User user = new QUser()
                .username.equalTo(loginDto.getUsername())
                .findOneOrEmpty()
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(loginDto.getPassword()), user.getPassword())) {
            return user;
        }

        throw new AppException("Invalid password", HttpStatus.UNAUTHORIZED);
    }

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

}
