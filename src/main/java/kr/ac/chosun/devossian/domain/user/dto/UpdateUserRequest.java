package kr.ac.chosun.devossian.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateUserRequest {
    private String useremail;
    private String userpassword;
    private String userschoolID;
    private String username;
}
