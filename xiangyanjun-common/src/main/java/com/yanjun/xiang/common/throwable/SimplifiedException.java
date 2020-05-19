package com.yanjun.xiang.common.throwable;


import com.yanjun.xiang.common.util.ErrorCode;
import org.springframework.util.StringUtils;

public class SimplifiedException extends RuntimeException  {
    private static final long serialVersionUID = 3761977150343281224L;
    private ErrorCode exceptionType;

    public SimplifiedException(ErrorCode exceptionType, Throwable e) {
        super(e);
        this.exceptionType = ErrorCode.OTHER;
        this.exceptionType = exceptionType;
    }

    public SimplifiedException(ErrorCode exceptionType) {
        this.exceptionType = ErrorCode.OTHER;
        this.exceptionType = exceptionType;
    }

    public SimplifiedException(ErrorCode exceptionType, String message) {
        super(message);
        this.exceptionType = ErrorCode.OTHER;
        this.exceptionType = exceptionType;
    }

    public SimplifiedException(ErrorCode exceptionType, String message, Throwable e) {
        super(message, e);
        this.exceptionType = ErrorCode.OTHER;
        this.exceptionType = exceptionType;
    }

    public SimplifiedException(String message, Throwable e) {
        super(message, e);
        this.exceptionType = ErrorCode.OTHER;
    }

    public SimplifiedException(String message) {
        super(message);
        this.exceptionType = ErrorCode.OTHER;
    }

    public SimplifiedException(Throwable e) {
        super(e);
        this.exceptionType = ErrorCode.OTHER;
    }

    public SimplifiedException() {
        this.exceptionType = ErrorCode.OTHER;
        this.exceptionType = ErrorCode.OTHER;
    }

    public ErrorCode getExceptionType() {
        return this.exceptionType;
    }

    public String getMessage() {
        return StringUtils.isEmpty(super.getMessage()) ? this.exceptionType.toString() : this.exceptionType.toString() + ':' + super.getMessage();
    }

    public String getSimpleMessage() {
        return StringUtils.isEmpty(super.getMessage()) ? this.exceptionType.toString() : super.getMessage();
    }

    public String getLocalizedMessage() {
        return StringUtils.isEmpty(super.getLocalizedMessage()) ? this.exceptionType.toString() : this.exceptionType.toString() + ':' + super.getLocalizedMessage();
    }

    public void printStackTrace() {
        synchronized(System.err) {
            System.err.println(this.exceptionType.toString());
        }

        super.printStackTrace();
    }

    public String toString() {
        String s = this.getClass().getName();
        String message = this.getMessage();
        return message != null ? s + ": " + message : s;
    }
}
