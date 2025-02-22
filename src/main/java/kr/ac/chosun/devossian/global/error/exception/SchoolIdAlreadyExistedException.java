package kr.ac.chosun.devossian.global.error.exception;

import kr.ac.chosun.devossian.global.error.ErrorCodeMessage;

public class SchoolIdAlreadyExistedException extends CustomException {
    public SchoolIdAlreadyExistedException() {
        super(ErrorCodeMessage.SCHOOLID_EXISTED);
    }
}
