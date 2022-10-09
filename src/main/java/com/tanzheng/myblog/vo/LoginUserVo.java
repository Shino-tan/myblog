package com.tanzheng.myblog.vo;

import lombok.Data;

@Data
public class LoginUserVo {

//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String account;

    private String nickname;

    private String avatar;
}
