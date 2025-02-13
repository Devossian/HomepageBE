package kr.ac.chosun.devossian.domain.user.dto;

import kr.ac.chosun.devossian.domain.user.Enum.Role;
import lombok.Data;

@Data
public class UserRegisterRequest {
    private String useremail;
    private String userpassword;
    private String userschoolID;
    private String username;
    private Role userrole;
}
