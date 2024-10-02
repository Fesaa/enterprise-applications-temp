package art.ameliah.ehb.anki.api.controllers;

import art.ameliah.ehb.anki.api.dtos.account.LoginDto;
import art.ameliah.ehb.anki.api.dtos.account.LoginResponse;
import art.ameliah.ehb.anki.api.dtos.account.RegisterDto;
import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.services.JwtService;
import art.ameliah.ehb.anki.api.services.model.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final JwtService jwtService;
    private final IAccountService accountService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginDto loginDto) {
        User user = accountService.login(loginDto);
        return LoginResponse.builder()
                .user(user)
                .token(jwtService.createToken(user))
                .build();
    }

    @PostMapping("/register")
    public LoginResponse register(@RequestBody RegisterDto registerDto) {
        User user = accountService.register(registerDto);
        return LoginResponse.builder()
                .user(user)
                .token(jwtService.createToken(user))
                .build();
    }

    @GetMapping("/roles")
    public List<String> getRoles() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

}
