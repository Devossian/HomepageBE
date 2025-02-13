package kr.ac.chosun.devossian.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCodeMessage {

    // User
    REGISTER_SUCCESS(200, "회원가입에 성공하였습니다."),
    LOGIN_SUCCESS(200, "로그인에 성공하였습니다."),
    USER_PROFILE_UPDATE_SUCCESS(200, "유저 정보 수정에 성공하였습니다."),
    USER_PAGE_VIEW_SUCCESS(200, "유저 페이지 조회에 성공하였습니다."),
    USER_PROFILE_IMAGE_UPDATE_SUCCESS(200, "유저 프로필 이미지 변경에 성공하였습니다."),
    USER_PROFILE_IMAGE_REMOVE_SUCCESS(200, "유저 프로필 이미지 삭제에 성공하였습니다."),
    USER_MENU_VIEW_SUCCESS(200, "유저 로그인 응답 조회에 성공하였습니다."),
//    GET_USER_POST_SUCCESS(200, "유저 게시물 조회에 성공하였습니다."),


    // Post
    POST_SUCCESS(200, "게시물 작성에 성공하였습니다."),
    POST_UPDATE_SUCCESS(200, "게시물 수정에 성공하였습니다."),
    POST_DELETE_SUCCESS(200, "게시물 삭제에 성공하였습니다."),
    USER_POST_VIEW_SUCCESS(200, "유저 게시물 조회에 성공하였습니다."),
    GET_FOLLOW_USER_POST_SUCCESS(200, "팔로잉 유저 포스트 조회에 성공하였습니다."),
    GET_NOT_FOLLOW_USER_POST_SUCCESS(200, "팔로잉 하지 않은 유저 포스트 조회에 성공하였습니다.");


    private final int status;
    private final String message;
}

