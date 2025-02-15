package kr.ac.chosun.devossian.domain.user.dto;

import kr.ac.chosun.devossian.domain.user.Enum.Role;
import kr.ac.chosun.devossian.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
// @Schema(description ="유저 DTO 반환") 스웨거 사용시 해제
public class UserDTO {
    private String useremail;
    private String userschoolID;
    private String username;
    private Role userrole;

    public UserDTO(User user) {
        this.useremail = user.getEmail();
        this.userschoolID = user.getSchoolId();
        this.username = user.getName();
        this.userrole = user.getRole();
    }
}
