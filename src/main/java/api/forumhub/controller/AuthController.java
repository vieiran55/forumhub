package api.forumhub.controller;

import api.forumhub.model.AuthUser;
import api.forumhub.config.TokenData;
import api.forumhub.config.TokenService;
import api.forumhub.config.AuthData;
import api.forumhub.repository.AuthRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    AuthRepository authRepository;

    @Autowired
    TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenData> auth(@RequestBody @Valid AuthData authData) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(authData.username(), authData.password());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((AuthUser) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenData(tokenJWT));
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}