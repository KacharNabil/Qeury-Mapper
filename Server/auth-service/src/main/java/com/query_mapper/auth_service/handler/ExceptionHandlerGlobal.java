package com.query_mapper.auth_service.handler;

import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;

public class ExceptionHandlerGlobal  {

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException (LockedException exp) {
            return  ResponseEntity
                    .status(UNAUTHORIZED)
                    .body(
                            ExceptionResponse.builder()
                                    .BusinessErrorCode(BusinessErrorCode.ACCOUNT_LOCKED.getCode())
                                    .BusinessErrorMessage(BusinessErrorCode.ACCOUNT_LOCKED.getMessage())
                                    .errorMessage(exp.getMessage())
                                    .build()
                    );
    }
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException (DisabledException exp) {
            return  ResponseEntity
                    .status(UNAUTHORIZED)
                    .body(
                            ExceptionResponse.builder()
                                    .BusinessErrorCode(BusinessErrorCode.ACCOUNT_DISABLED.getCode())
                                    .BusinessErrorMessage(BusinessErrorCode.ACCOUNT_DISABLED.getMessage())
                                    .errorMessage(exp.getMessage())
                                    .build()
                    );
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException (BadCredentialsException exp) {
            return  ResponseEntity
                    .status(UNAUTHORIZED)
                    .body(
                            ExceptionResponse.builder()
                                    .BusinessErrorCode(BusinessErrorCode.BAD_CREDENTIALS.getCode())
                                    .BusinessErrorMessage(BusinessErrorCode.BAD_CREDENTIALS.getMessage())
                                    .errorMessage(BusinessErrorCode.BAD_CREDENTIALS.getMessage())
                                    .build()
                    );
    }
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException (MessagingException exp) {
            return  ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(
                            ExceptionResponse.builder()
                                    .errorMessage(exp.getMessage())
                                    .build()
                    );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException (MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
         exp.getBindingResult().getAllErrors().forEach(error -> {
             var errorMessage = error.getDefaultMessage();
             errors.add(errorMessage);
         });
        return  ResponseEntity
                    .status(BAD_REQUEST)
                    .body(
                            ExceptionResponse.builder()
                                    .ValidationErrors(errors)
                                    .build()
                    );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException (Exception exp) {
        
        exp.printStackTrace();
        return  ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(
                            ExceptionResponse.builder()
                                    .BusinessErrorMessage("INTERNAL SERVER ERROR")
                                    .errorMessage(exp.getMessage())
                                    .build()
                    );
    }

}
