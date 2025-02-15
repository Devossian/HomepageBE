package kr.ac.chosun.devossian.global.error.exception;

import kr.ac.chosun.devossian.global.error.ErrorCodeMessage;

public class EntityNotExistedException extends CustomException {

    public EntityNotExistedException(ErrorCodeMessage message) {
        super(message);
    }
}
