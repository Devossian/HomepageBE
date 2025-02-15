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
//id, email, password, school_number, name,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name ="email", nullable = false, unique = true)
    private String email; // 아이디: chosun@chosun.ac.kr, chosun@chosun.kr

    @Column(name ="password", nullable = false, length= 255)
    private String password; // 비밀번호

    @Column(name = "school_id", nullable = false, unique = true )
    private String schoolId; // 학번: 학생:12345678, 교원:123456

    @Column(name = "name", nullable = false)
    private String name; // 실명: 홍길동

    @Enumerated(EnumType.STRING)
    private Role role;

    private String refreshToken; // JWT 리프레시 토큰 저장

//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "imageUrl", column = @Column(name = "user_img_url")),
//            @AttributeOverride(name = "imageType", column = @Column(name = "user_img_type")),
//            @AttributeOverride(name = "imageName", column = @Column(name = "user_img_name")),
//            @AttributeOverride(name = "imageUUID", column = @Column(name = "user_img_uuid"))
//    })
//    private Image image; // 프로필 이미지 --global에 image/image.java 생성
    @Builder
    public User(String email, String password, String schoolId, String name) {
        this.email = email;
        this.password = password;
        this.schoolId = schoolId;
        this.name = name;
        this.role = Role.USER;
    }

    public void setEncPassword(BCryptPasswordEncoder bCryptPasswordEncoder){
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

    //리프레시 토큰 업데이트
    public void updateRefreshToken(String updateRefreshToken){
        this.refreshToken = updateRefreshToken;
    }
}
