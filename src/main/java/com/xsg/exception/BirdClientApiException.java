package com.xsg.exception;

import lombok.Data;

/**
 * @author xue shengguo
 * @datetime 17/8/15 下午3:23
 */
@Data
public class BirdClientApiException extends RuntimeException {
    private int code;
    private String message;

    public BirdClientApiException(Exception e) {
        super(e);
    }

    public BirdClientApiException(String message, Exception e) {
        super(message, e);
    }

    public BirdClientApiException(int code, String message) {
        super(String.format("code is (%d) message is (%s)", code, message));
        this.code = code;
        this.message = message;
    }

    public BirdClientApiException(int code, String message, Exception e) {
        super(String.format("code is (%d) message is (%s)", code, message), e);
        this.code = code;
        this.message = message;
    }
}
