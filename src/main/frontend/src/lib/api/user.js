import client from './client';

//개인 벨로그 정보 블러오기
export const user = (userId) => client.get(`/user/${userId}`);

export const likelist = (page) => client.get(`/user/likelist?page=${page}`);

//유저 이미지 업로드
export const userImg = ({ userId, data }) => {
  // for (var pair of data.entries()) {
  //   console.log(pair[0] + ', ' + pair[1]);
  // }
  return client.put(`/user/${userId}/profileImageUrl`, data, config); //Json화 시켜줘서 오류가 떳구나!!! multipart인데!!! return문 또 빼먹었네..
};

const config = {
  headers: { 'content-type': `multipart/form-data;` },
};
