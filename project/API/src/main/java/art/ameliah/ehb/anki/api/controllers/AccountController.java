package art.ameliah.ehb.anki.api.controllers;

import art.ameliah.ehb.anki.api.annotations.AllowAnonymous;
import art.ameliah.ehb.anki.api.annotations.BaseController;
import art.ameliah.ehb.anki.api.dtos.account.LoginDto;
import art.ameliah.ehb.anki.api.dtos.account.RegisterDto;
import art.ameliah.ehb.anki.api.dtos.account.UserDto;
import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.services.JwtService;
import art.ameliah.ehb.anki.api.services.model.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@BaseController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final JwtService jwtService;
    private final IAccountService accountService;
    private final ModelMapper modelMapper;

    @AllowAnonymous
    @PostMapping("/login")
    public UserDto login(@RequestBody LoginDto loginDto) {
        User user = accountService.login(loginDto);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setToken(jwtService.createToken(user));
        return userDto;
    }

    @AllowAnonymous
    @PostMapping("/register")
    public UserDto register(@RequestBody RegisterDto registerDto) {
        User user = accountService.register(registerDto);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setToken(jwtService.createToken(user));
        return userDto;
    }

    @AllowAnonymous
    @GetMapping("/has-any-account")
    public boolean hasRegisteredAccount() {
        return accountService.anyAccount();
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
