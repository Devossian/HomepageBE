package kr.ac.chosun.devossian.domain.user.controller;

import kr.ac.chosun.devossian.domain.user.dto.*;
import kr.ac.chosun.devossian.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(new UserDTO(userService.signUp(request)));
    }

    // 로그인: 이메일과 비밀번호를 받아 JWT 토큰을 반환
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse tokens = userService.login(request.getUseremail(), request.getUserpassword());
        return ResponseEntity.ok(tokens);
    }

    // 로그아웃: 요청 헤더의 액세스 토큰을 기반으로 로그아웃 처리
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String accessToken) {
        userService.logout(accessToken);
        return ResponseEntity.ok().build();
    }

    // 토큰 재발급: 리프레시 토큰을 받아 새 토큰을 발급
    @PostMapping("/token/reissue")
    public ResponseEntity<TokenResponse> reissueToken(@RequestBody TokenReissueRequest request) {
        TokenResponse tokens = userService.reissueToken(request.getRefreshToken());
        return ResponseEntity.ok(tokens);
    }

    // 유저정보 조회: 인증 정보를 기반으로 유저정보 반환
    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUserInfo(Authentication authentication) {
        UserDTO dto = userService.getUserInfo(authentication.getName());
        return ResponseEntity.ok(dto);
    }

    // 유저정보 수정: 이메일, 비밀번호, 학번, 이름 등을 수정
    @PutMapping("/user")
    public ResponseEntity<UserDTO> updateUserInfo(Authentication authentication,
                                                  @RequestBody UpdateUserRequest request) {
        UserDTO dto = userService.updateUser(authentication.getName(), request);
        return ResponseEntity.ok(dto);
    }

    // 회원탈퇴: 인증된 사용자 계정을 삭제
    @DeleteMapping("/user")
    public ResponseEntity<Void> deleteUser(Authentication authentication) {
        userService.deleteUser(authentication.getName());
        return ResponseEntity.ok().build();
    }
}
