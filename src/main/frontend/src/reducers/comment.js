import produce from 'immer';
import { createAction, handleActions } from 'redux-actions';
import { takeLatest } from 'redux-saga/effects';
import * as commentAPI from '../lib/api/comment';
import { createRequestActionTypes, createRequestSaga } from '../lib/createRequestSaga';

const [COMMENT_POST_REQUEST, COMMENT_POST_SUCCESS, COMMENT_POST_FAILURE] = createRequestActionTypes('COMMENT_POST');
const [COMMENT_DELETE_REQUEST, COMMENT_DELETE_SUCCESS, COMMENT_DELETE_FAILURE] = createRequestActionTypes('COMMENT_DELETE');

export const commentPostsAction = createAction(COMMENT_POST_REQUEST, ({ content, postId }) => ({ content, postId }));
export const commentDeleteAction = createAction(COMMENT_DELETE_REQUEST, (data) => data);

const commentPostsSaga = createRequestSaga(COMMENT_POST_REQUEST, commentAPI.save);
const commentDeleteSaga = createRequestSaga(COMMENT_DELETE_REQUEST, commentAPI.deleteById);

export function* commentSaga() {
  //이벤트 리스너!
  yield takeLatest(COMMENT_POST_REQUEST, commentPostsSaga);
  yield takeLatest(COMMENT_DELETE_REQUEST, commentDeleteSaga);
}

const initialState = {
  //게시글 작성
  commentPostDone: false,
  commentPostError: null,

  //게시글 삭제
  commentDeleteDone: false,
  commentDeleteError: null,

  comment: null,
  cmRespDto: null,
  error: null,
};

const comment = handleActions(
  {
    //댓글쓰기
    [COMMENT_POST_REQUEST]: (state) =>
      produce(state, (draft) => {
        draft.commentPostDone = false;
        draft.commentPostError = null;
      }),
    [COMMENT_POST_SUCCESS]: (state, { payload: data }) => ({
      ...state,
      commentPostError: null,
      commentPostDone: true,
      cmRespDto: data,
      comment: data.data,
    }),
    [COMMENT_POST_FAILURE]: (state, { payload: error }) => ({
      ...state,
      commentPostError: error,
    }),

    //댓글삭제
    [COMMENT_DELETE_REQUEST]: (state) =>
      produce(state, (draft) => {
        draft.commentDeleteDone = false;
        draft.commentDeleteError = null;
      }),
    [COMMENT_DELETE_SUCCESS]: (state, { payload: data }) => ({
      ...state,
      commentDeleteError: null,
      commentDeleteDone: true,
      cmRespDto: data,
    }),
    [COMMENT_DELETE_FAILURE]: (state, { payload: error }) => ({
      ...state,
      commentDeleteError: error,
    }),
  },
  initialState,
);

export default comment;
