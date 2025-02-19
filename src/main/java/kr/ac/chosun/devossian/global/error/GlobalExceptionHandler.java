package kr.ac.chosun.devossian.global.error;

import kr.ac.chosun.devossian.global.error.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handlerException(CustomException e){
        ErrorCodeMessage errorCodeMessage = e.getErrorCodeMessage();
        ErrorResponseDTO response = ErrorResponseDTO.of(errorCodeMessage, e.getErrors());
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCodeMessage.getStatus()));
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        ErrorResponseDTO response = ErrorResponseDTO.of(ErrorCodeMessage.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 입력 검증 예외
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return ResponseEntity.badRequest()
                .body(ErrorResponseDTO.of(ErrorCodeMessage.INVALID_POST_REQUEST, bindingResult));
    }

    // 잘못된 JSON 요청 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(ErrorResponseDTO.of(ErrorCodeMessage.INVALID_POST_REQUEST));
    }
}
