import { takeLatest } from '@redux-saga/core/effects';
import { createAction, handleActions } from 'redux-actions';
import * as tagAPI from '../lib/api/tag';
import { createRequestActionTypes, createRequestSaga } from '../lib/createRequestSaga';
import { produce } from 'immer';

const initialState = {
  tagSearchDone: false,
  tagSearchError: null,
  data: null,
  cmRespDto: null,
};

const [TAG_REQUST, TAG_SUCCESS, TAG_FAILURE] = createRequestActionTypes('TAG');

//액션 생성 함수
export const tagAction = createAction(TAG_REQUST, (data) => data);

//사가 생성
const tagSearchSaga = createRequestSaga(TAG_REQUST, tagAPI.searchByName);

export function* tagSaga() {
  //이벤트 리스너!
  yield takeLatest(TAG_REQUST, tagSearchSaga);
}

//리듀서
const tag = handleActions(
  {
    //태그 관련 게시글 찾기
    [TAG_REQUST]: (state) =>
      produce(state, (draft) => {
        draft.tagSearchDone = false;
        draft.tagSearchError = null;
      }),
    [TAG_SUCCESS]: (state, { payload: data }) => ({
      ...state,
      tagSearchError: null,
      tagSearchDone: true,
      cmRespDto: data,
      data: data.data,
    }),
    [TAG_FAILURE]: (state, { payload: error }) => ({
      ...state,
      tagSearchError: error,
    }),
  },
  initialState,
);

export default tag;
