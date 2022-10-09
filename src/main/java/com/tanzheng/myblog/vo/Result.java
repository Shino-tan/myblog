package com.tanzheng.myblog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {

    private boolean success;

    private int code;

    private String msg;

    private Object data;

    public static Result success(Object data) {
        return new Result(true, 200, "成功", data);
    }

    public static Result fail(int coed, String msg) {
        return new Result(false, coed, msg, null);
    }


}
