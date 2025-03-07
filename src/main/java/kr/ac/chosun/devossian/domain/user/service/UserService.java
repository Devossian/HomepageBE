package kr.ac.chosun.devossian.domain.user.service;

import jakarta.transaction.Transactional;
import kr.ac.chosun.devossian.domain.user.domain.User;
import kr.ac.chosun.devossian.domain.user.dto.UserRegisterRequest;
import kr.ac.chosun.devossian.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User signUp(UserRegisterRequest userRegisterRequest) {

        if(userRepository.existsByEmail(userRegisterRequest.getUseremail())){
            //throw new UseremailAlreadyException();
        }

        if(userRepository.existsBySchoolId(userRegisterRequest.getUserschoolID())){
            //throw new SchoolIDAlreadyExistedException();
        }

        User user = User.builder()
                .email(userRegisterRequest.getUseremail())
                .password(userRegisterRequest.getUserpassword())
                .schoolId(userRegisterRequest.getUserschoolID())
                .name(userRegisterRequest.getUsername())
                .build();

        user.setEncPassword(bCryptPasswordEncoder);

        return userRepository.save(user);
    }

}
