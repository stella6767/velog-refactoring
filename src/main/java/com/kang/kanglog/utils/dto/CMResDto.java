package com.kang.kanglog.utils.dto;


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
