package kr.ac.chosun.devossian.domain.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import kr.ac.chosun.devossian.domain.user.Enum.Role;
import kr.ac.chosun.devossian.global.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Entity
@AllArgsConstructor
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseTimeEntity {
    // 필드 선언
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name ="email", nullable = false, unique = true)
    private String email;

    @Column(name ="password", nullable = false, length= 255)
    private String password;

    @Column(name = "school_id", nullable = false, unique = true )
    private String schoolId;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String refreshToken;

    @Builder
    public User(String email, String password, String schoolId, String name) {
        this.email = email;
        this.password = password;
        this.schoolId = schoolId;
        this.name = name;
        this.role = Role.USER;
    }

    public void setEncPassword(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.password = bCryptPasswordEncoder.encode(this.password);
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    // 비밀번호 업데이트 메서드를 클래스 내부에 추가합니다.
    public void updatePassword(String newPassword, BCryptPasswordEncoder encoder) {
        this.password = encoder.encode(newPassword);
    }
}
