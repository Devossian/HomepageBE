package kr.ac.chosun.devossian.domain.user.service;

import jakarta.transaction.Transactional;
import kr.ac.chosun.devossian.domain.user.domain.User;
import kr.ac.chosun.devossian.domain.user.dto.*;
import kr.ac.chosun.devossian.domain.user.exception.UseremailAlreadyExistedException;
import kr.ac.chosun.devossian.domain.user.repository.UserRepository;
import kr.ac.chosun.devossian.global.error.exception.SchoolIdAlreadyExistedException;
import kr.ac.chosun.devossian.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입 (기존 코드와 동일)
    public User signUp(UserRegisterRequest request) {
        // 요청 객체 자체가 null인 경우
        if(request == null) {
            throw new IllegalArgumentException("회원가입 요청 정보가 없습니다.");
        }

        // 필수 값 검증 (이메일, 비밀번호, 학번, 이름)
        if(request.getUseremail() == null || request.getUseremail().trim().isEmpty()) {
            throw new IllegalArgumentException("이메일을 입력해주세요.");
        }
        if(!isValidEmail(request.getUseremail())) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }
        if(request.getUserpassword() == null || request.getUserpassword().trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }
        if(request.getUserschoolID() == null || request.getUserschoolID().trim().isEmpty()) {
            throw new IllegalArgumentException("학번을 입력해주세요.");
        }
        if(request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("이름을 입력해주세요.");
        }

        //DB 중복체크
        if(userRepository.existsByEmail(request.getUseremail())){
            throw new UseremailAlreadyExistedException();
        }
        if(userRepository.existsBySchoolId(request.getUserschoolID())){
            throw new SchoolIdAlreadyExistedException();
        }


        User user = User.builder()
                .email(request.getUseremail())
                .password(request.getUserpassword())
                .schoolId(request.getUserschoolID())
                .name(request.getUsername())
                .build();
        user.setEncPassword(bCryptPasswordEncoder);

        return userRepository.save(user);
    }

    // 로그인: 이메일과 비밀번호 확인 후 JWT 토큰 발급
    public TokenResponse login(String useremail, String rawPassword) {
        User user = userRepository.findByEmail(useremail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!bCryptPasswordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        String accessToken = jwtTokenProvider.createAccessToken(useremail, user.getRole().name());
        String refreshToken = jwtTokenProvider.createRefreshToken(useremail);
        user.updateRefreshToken(refreshToken);
        userRepository.save(user);
        return new TokenResponse(accessToken, refreshToken);
    }

    // 로그아웃: 저장된 리프레시 토큰 제거 처리
    public void logout(String accessToken) {
        String useremail = jwtTokenProvider.getEmailFromToken(accessToken);
        User user = userRepository.findByEmail(useremail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.updateRefreshToken(null);
        userRepository.save(user);
    }

    // 토큰 재발급: 전달받은 리프레시 토큰 검증 후 새 토큰 발급
    public TokenResponse reissueToken(String refreshToken) {
        if(!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BadCredentialsException("Invalid refresh token");
        }
        String useremail = jwtTokenProvider.getEmailFromToken(refreshToken);
        User user = userRepository.findByEmail(useremail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new BadCredentialsException("Refresh token mismatch");
        }
        String newAccessToken = jwtTokenProvider.createAccessToken(useremail, user.getRole().name());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(useremail);
        user.updateRefreshToken(newRefreshToken);
        userRepository.save(user);
        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    // 유저정보 조회
    public UserDTO getUserInfo(String useremail) {
        User user = userRepository.findByEmail(useremail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserDTO(user);
    }

    // 유저정보 수정: 이메일, 이름, 학번, 비밀번호 등의 업데이트 처리
    public UserDTO updateUser(String useremail, UpdateUserRequest request) {
        User user = userRepository.findByEmail(useremail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(request.getUseremail() != null && !request.getUseremail().equals(user.getEmail())){
            if(userRepository.existsByEmail(request.getUseremail())){
                throw new UseremailAlreadyExistedException();
            }
            user.updateEmail(request.getUseremail());
        }
        if(request.getUsername() != null){
            user.updateName(request.getUsername());
        }
        if(request.getUserschoolID() != null && !request.getUserschoolID().equals(user.getSchoolId())){
            if(userRepository.existsBySchoolId(request.getUserschoolID())){
                throw new SchoolIdAlreadyExistedException();
            }
            user.updateSchoolId(request.getUserschoolID());
        }
        if(request.getUserpassword() != null){
            // User 엔티티에 updatePassword() 메서드를 추가해야 합니다.
            user.updatePassword(request.getUserpassword(), bCryptPasswordEncoder);
        }
        userRepository.save(user);
        return new UserDTO(user);
    }

    // 회원탈퇴
    public void deleteUser(String useremail) {
        User user = userRepository.findByEmail(useremail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userRepository.delete(user);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
}
