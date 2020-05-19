package com.yanjun.xiang.common.throwable;


import com.yanjun.xiang.common.util.ErrorCode;

public class BizException extends SimplifiedException {
    public BizException(ErrorCode exceptionType, Throwable e) {
        super(exceptionType, e);
    }

    public BizException(ErrorCode exceptionType) {
        super(exceptionType);
    }

    public BizException(ErrorCode exceptionType, String message) {
        super(exceptionType, message);
    }

    public BizException(ErrorCode exceptionType, String message, Throwable e) {
        super(exceptionType, message, e);
    }

    public BizException(String message, Throwable e) {
        super(message, e);
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(Throwable e) {
        super(e);
    }

    public BizException() {
        super();
    }
}
