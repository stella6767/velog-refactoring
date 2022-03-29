

굳이 JWT를 쓰는 이유

HttpSession

axios request에 axios.defaults.withCredentials = true;
를 주어서 토큰 인증을 사용한다는 설정을 추가했다.

spring boot에는 WebConfig설정에 .allowCredentials(true)를 설정했다.

이를 위해
개발 단계라서 allowOrigins("*")을 주었던 것을 특정 url로 변경해주었다.

왜냐면..
allowCredentials=true 설정과 allowOrigins=* 함께 사용할 수 없기 때문.

https://lms0806.tistory.com/112