import client from './client';

export const save = ({ content, postId }) => {
  console.log('content', content, 'postId', postId);

  return client.post(`/comment/${postId}`, JSON.stringify(content));
};

export const deleteById = (id) => {
  return client.delete(`/comment/${id}`);
};
