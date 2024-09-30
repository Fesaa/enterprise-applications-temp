package art.ameliah.ehb.api.controllers;

import art.ameliah.ehb.api.dtos.account.LoginDto;
import art.ameliah.ehb.api.dtos.account.LoginResponse;
import art.ameliah.ehb.api.dtos.account.RegisterDto;
import art.ameliah.ehb.api.models.account.Role;
import art.ameliah.ehb.api.models.account.User;
import art.ameliah.ehb.api.services.AccountService;
import art.ameliah.ehb.api.services.JwtService;
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
    private final AccountService accountService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginDto loginDto) {
        User user = accountService.login(loginDto);
        return LoginResponse.builder()
                .username(user.getUsername())
                .token(jwtService.createToken(user))
                .build();
    }

    @PostMapping("/register")
    public LoginResponse register(@RequestBody RegisterDto registerDto) {
        User user = accountService.register(registerDto);
        return LoginResponse.builder()
                .username(user.getUsername())
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
