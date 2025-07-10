package com.gswep.insurance.user.controller;

import com.gswep.insurance.user.repository.UserRepository;
import com.gswep.insurance.user.requestDTO.LoginRequestDto;
import com.gswep.insurance.user.requestDTO.TokenDTO;
import com.gswep.insurance.user.requestDTO.UserRequestDTO;
import com.gswep.insurance.user.response.UserResponseDTO;
import com.gswep.insurance.user.service.AuthService;
import com.gswep.insurance.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final AuthService authService;

    private final UserService userService;

    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signUp(@RequestBody UserRequestDTO requestDTO) {
        UserResponseDTO registeredUser = authService.signUp(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        log.info("로그인 요청 - 이메일 : {}",loginRequestDto.getEmail());
        System.out.println(loginRequestDto.getEmail());
        String tokenDTO = authService.login(loginRequestDto);
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refreshToken(@RequestBody String refreshToken){
        TokenDTO newTokens = authService.reissueAccessToken(refreshToken);
        return ResponseEntity.ok(newTokens);
    }
}
