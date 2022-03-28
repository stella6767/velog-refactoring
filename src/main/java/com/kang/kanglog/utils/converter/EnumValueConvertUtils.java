package com.kang.kanglog.utils.converter;

//@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnumValueConvertUtils {
    //https://techblog.woowahan.com/2600/

    //https://recordsbeat.github.io/jpa/converter/ddd/ 참고

//    public static <T extends Enum<T> & CodeEnum> T ofDBCode(Class<T> enumClass, String dbCode){
//        if(StringUtils.isBlank(dbCode))
//            return null;
//
//        return EnumSet.allOf(enumClass).stream()
//                .filter(v -> v.getCode().equals(dbCode))
//                .findAny()
//                .orElseThrow(() -> new IllegalArgumentException(String.format("enum=[%s], code=[%s]가 존재하지 않습니다.",enumClass.getName(),dbCode)));
//    }


//    public static <T extends Enum<T> & CodeEnum> String toDBcode(T enumValue) {
//        return enumValue.getCode();
//    }
}


//public interface CodeEnum {
//    String getKey();
//    String getCode();
//    String getComment();
//}
//
//@Converter(autoApply = true)
//public class GenderConverter extends AbstractEnumAttributeConverter<Gender>{
//    public static final String ENUM_NAME = "성별";
//
//    public GenderConverter(){
//        super(Gender.class,false,ENUM_NAME);
//    }
//}
//
//public enum DeviceType implements CodeEnum