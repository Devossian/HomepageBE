package kr.ac.chosun.devossian.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenReissueRequest {
    private String refreshToken;
}
