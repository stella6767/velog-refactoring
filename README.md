

# 퇴근 후 사이드 프로젝트

코딩에 지겨움을 느끼는 중, 사이드 프로젝트를 하라는 조언을 보고, 사이드 프로젝트를 시작하기로 마음먹었다. 
그렇다고 완전히 새로운 프로젝트를 하기에는 아이디어도 안 떠오르고 여력도 없는 차, 취업 전에 혼자서 만들다 만 포트폴리오가 생각이 났다. 
도커로 배포파일 만들다 만 프로젝트인데, 여기서 조금 더 살을 얹고 CI/CD 환경까지 구축해서 EC2에 배포하는 것 까지 해볼련다.

# 기술스택

## 벡엔드
- springboot
- Intellij
- gradle 7.4.1
- java 11
- jpa
- mariaDB - AWS RDS


## 프론트엔드
- node.js 16.13.2
- react 17.0.2
- redux / redux-saga
- axios
- styled component
- antd
- dotenv
- react-quill

## dev
- AWS EC2
- AWS S3
- Travis CI



# ERD



# 소개





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