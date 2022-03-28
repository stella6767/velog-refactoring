package com.kang.kanglog.utils.common;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Arrays;

//@NoArgsConstructor
@Getter
public class SearchCondition {
    SearchType searchType;
    String keyword;

    //https://kapentaz.github.io/java/spring/Enum-and-Jackson-in-Spring/#
    // Jackson에서는 기본으로 EnumDeserializer가 있고 이미 설정되어 있어서 Enum의 name으로 변환하는 것은 따로 신경 쓸 부분이 없슴
    //Enum의 name이 아니라 별도로 정의한 code 값으로 처리하고 싶을 때는 @JsonCreator와 @JsonValue를 이용

    @JsonCreator
    public SearchCondition(@JsonProperty("searchType") String searchType, @JsonProperty("keyword") String keyword) {
        this.searchType = convertEnumType(searchType);
        this.keyword = convertKeyword(keyword);
    }

    private String convertKeyword(String keyword) {
        return (StringUtils.hasText(keyword)) ? keyword : null;
    }


    private SearchType convertEnumType(String searchType) {

        if (searchType == null){
            return null;
        }
        return Arrays.stream(SearchType.values())
                .filter(type -> type.name().equals(searchType)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("검색타입 인자를 잘못 보냈습니다.") );
    }

}
