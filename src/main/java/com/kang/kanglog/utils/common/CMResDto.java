package com.kang.kanglog.utils.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CMResDto<T> {

    private T resultCode;
    private String resultMsg;
    private T data;

}
