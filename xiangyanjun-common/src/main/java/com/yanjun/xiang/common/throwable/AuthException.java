package com.yanjun.xiang.common.throwable;

import com.yanjun.xiang.common.util.ErrorCode;

public class AuthException extends SimplifiedException {

    public AuthException(ErrorCode exceptionType) {
        super(exceptionType);
    }

    public AuthException(ErrorCode exceptionType, String message) {
        super(exceptionType, message);
    }
}
