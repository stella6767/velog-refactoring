package com.kang.kanglog.utils.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CMResDto<T> {

    private T resultCode;  //resultCode를 enum으로 관리하는 게 더 깔끔하겠지만. 1 = 성공 / 0 = 그 외
    private String resultMsg;
    private T data;

}
