package kr.ac.chosun.devossian.domain.user.repository;

import kr.ac.chosun.devossian.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findBySchoolId(String schoolId);

    Optional<User> findByRefreshToken(String refreshToken);

    boolean existsByEmail(String email);
    boolean existsBySchoolId(String schoolId);
}
