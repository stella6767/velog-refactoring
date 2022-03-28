
#노드를 build라는 이름으로 다운받고
FROM node:12.4.0-alpine as build

#app 폴더 하나 만들고 워크 디렉토리 설정
WORKDIR /app


ENV PATH /app/node_modules/.bin:$PATH

#안에 package.json을 docker 내부에 package.json으로 복사
COPY package.json /app/package.json

#console 창에 message 띄우지 말고 조용하게 다운
RUN npm install --silent

#
RUN npm install react-scripts@3.0.1 -g --silent

#velog-frontend 안에 있는 모든 파일을 도커 내부의 app 폴더로 복사
COPY . /app

#빌드파일 생성
RUN npm run build


# 요거 안 쓸 거임...ㄴㄴ


