package art.ameliah.ehb.anki.api.controllers;

import art.ameliah.ehb.anki.api.annotations.AllowAnonymous;
import art.ameliah.ehb.anki.api.annotations.BaseController;
import art.ameliah.ehb.anki.api.dtos.account.LoginDto;
import art.ameliah.ehb.anki.api.dtos.account.LoginResponse;
import art.ameliah.ehb.anki.api.dtos.account.RegisterDto;
import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.services.JwtService;
import art.ameliah.ehb.anki.api.services.model.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@BaseController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final JwtService jwtService;
    private final IAccountService accountService;

    @AllowAnonymous
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginDto loginDto) {
        User user = accountService.login(loginDto);
        return LoginResponse.builder()
                .user(user)
                .token(jwtService.createToken(user))
                .build();
    }

    @AllowAnonymous
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
