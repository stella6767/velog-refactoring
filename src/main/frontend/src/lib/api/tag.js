import client from './client';

export const searchByName = (name) => {
  return client.get(`/tag/search?name=${name}`);
};
