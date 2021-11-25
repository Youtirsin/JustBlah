package com.youtirsin.blah.message;

public class ResultTool {

    public static MessageResult success() {
        return new MessageResult(ResultCode.SUCCESS);
    }
    
    public static <T> DataResult<T> success(T data) {
        return new DataResult<T>(data);
    }

    public static MessageResult fail() {
        return new MessageResult();
    }

    public static MessageResult fail(ResultCode resultEnum) {
        return new MessageResult(resultEnum);
    }
}
