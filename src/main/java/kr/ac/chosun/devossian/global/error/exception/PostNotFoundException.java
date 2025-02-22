package kr.ac.chosun.devossian.global.error.exception;

import kr.ac.chosun.devossian.global.error.ErrorCodeMessage;
import kr.ac.chosun.devossian.global.error.ErrorResponseDTO;

import java.util.List;

public class PostNotFoundException extends CustomException{
    public PostNotFoundException(ErrorCodeMessage errorCodeMessage) {
        super(errorCodeMessage);
    }

}
