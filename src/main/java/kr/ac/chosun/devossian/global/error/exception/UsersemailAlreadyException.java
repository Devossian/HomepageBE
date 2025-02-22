package kr.ac.chosun.devossian.global.error.exception;

import kr.ac.chosun.devossian.global.error.ErrorCodeMessage;

public class UsersemailAlreadyException extends CustomException {
    public UsersemailAlreadyException() {

      super(ErrorCodeMessage.USEREMAIL_EXISTED);
    }
}
