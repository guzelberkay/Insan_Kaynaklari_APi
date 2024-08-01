package org.takim2.insan_kaynaklari_api.exception;

import lombok.Getter;

@Getter
public class UserServiceException extends RuntimeException{
    private ErrorType errorType;

    public UserServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public UserServiceException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }
}
