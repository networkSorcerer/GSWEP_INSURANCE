package com.gswep.insurance.oauth.controller;

import com.gswep.insurance.oauth.dto.OAuth2LoginRequestDTO;
import com.gswep.insurance.user.requestDTO.TokenDTO;
import com.gswep.insurance.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class OAuthController {
    private final AuthService authService;

    @PostMapping("/google")
    public ResponseEntity<TokenDTO> googleLogin(@RequestBody OAuth2LoginRequestDTO loginRequestDTO){
        TokenDTO tokenDTO = authService.OAuth2Login(loginRequestDTO);
        return ResponseEntity.ok(tokenDTO);
    }
}
