package com.yanjun.xiang.common.throwable;

import org.spin.core.ErrorCode;
import org.spin.core.throwable.SimplifiedException;

public class AuthException extends SimplifiedException {

    public AuthException(ErrorCode exceptionType) {
        super(exceptionType);
    }

    public AuthException(ErrorCode exceptionType, String message) {
        super(exceptionType, message);
    }
}
