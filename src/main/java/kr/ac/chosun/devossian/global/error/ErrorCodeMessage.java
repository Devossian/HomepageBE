package kr.ac.chosun.devossian.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeMessage {

    UNKNOWN(500, "알 수 없는 오류가 발생하였습니다."),
    INTERNAL_SERVER_ERROR(500,  "내부 서버 오류입니다."),
    FILE_CONVERT_FAIL(500, "변환할 수 없는 파일입니다."),
    FILE_ALREADY_EXISTED(400, "이미 폴더에 존재하는 사진입니다."),

    // User
    USER_NOT_EXISTED(400,  "존재 하지 않는 유저입니다."),
    USERNAME_EXISTED(400,  "이미 존재하는 아이디 입니다."),
    NICKNAME_EXISTED(400,  "이미 존재하는 닉네임 입니다."),
    AUTHENTICATION_FAIL(401,  "로그인이 필요한 회원입니다."),
    PASSWORD_NOT_MATCH(401, "비밀번호가 일치하지 않습니다."),
    ACCOUNT_MISMATCH(401,  "회원 정보가 일치하지 않습니다."),
    AUTHORITY_INVALID(403,  "권한이 없습니다."),
    USER_SEARCH_FAIL(400, "검색된 유저가 존재하지 않습니다."),

    // TODO Status 변환하기
    JWT_REFRESH_TOKEN_EXPIRED(401, "만료된 리프레시 토큰입니다."),
    JWT_TOKEN_INVALID( 401, "유효하지 않은 토큰입니다."),
    JWT_TOKEN_EXPIRED( 401, "만료된 토큰입니다."),

    // FileConvert
    FILE_IMAGE_TYPE_MISMATCH(400, "파일 확장자가 지원되지 않습니다."),

    // Post
    POST_NOT_FOUND(400, "존재 하지 않는 글 입니다."),
    INVALID_POST_REQUEST(400, "입력 값이 잘못 되었습니다");

    private final int status;
    private final String message;
}
