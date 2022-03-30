import client from './client';

export const save = (comment) => {
  console.log('comment', comment);

  return client.post(`/comment`, JSON.stringify(comment));
};

export const deleteById = (id) => {
  return client.delete(`/comment/${id}`);
};
