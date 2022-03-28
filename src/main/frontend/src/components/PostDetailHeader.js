import React, { memo } from 'react';
import { StyledHeadDescDiv } from '../pages/user/style';
import { HeartOutlined, HeartFilled } from '@ant-design/icons';
import { useCallback } from 'react';
import { useDispatch } from 'react-redux';
import { getPostAction, likeDeleteAction, likePostAction, postDeleteAction } from '../reducers/post';
import { Link, useHistory } from 'react-router-dom';
import useUpdateEffect from '../lib/hooks/useUpdateEffect';
import { useState } from 'react';
import moment from 'moment';
import { StyledDetailTagLink, StyledLikeBtn, StyledPostDetailTagDiv, StyledUnlikeBtn } from './style';
import { Button } from 'antd';
import { useEffect } from 'react';
import { useSelector } from 'react-redux';

const PostDetailHeader = memo((props) => {
  const { userId, postId, principal, postDeleteDone, postDeleteError } = props;

  const { post, likePostDone, likePostError, likeDeleteDone, likeDeleteError } = useSelector(({ post }) => ({
    post: post.post,
    likePostDone: post.likePostDone,
    likePostError: post.likePostError,
    likeDeleteDone: post.likeDeleteDone,
    likeDeleteError: post.likeDeleteError,
  }));

  let history = useHistory();

  const dispatch = useDispatch();
  //이렇게 받아야지 redux data와 동기화가 안전하게 이루어짐.. 딱히 방법이 안 떠오르네.. ..뭐지..
  const [likeState, setLikeState] = useState(null);
  const [likeCount, setLikeCount] = useState(null);

  useEffect(() => {
    setLikeState(post.likeState); //안 되네......
    setLikeCount(post.likeCount);
    //console.log('likeState', post.likeState);
  }, [post]);

  useUpdateEffect(() => {
    if (likePostError) {
      alert('좋아요 등록에 실패하였습니다.');
    }

    //좋아요 됐으면
    if (likePostDone) {
      //console.log('둘 다 실행되는구만.. 하나의 useUpdateEffect에 4개 변수를 다 넣으니, 둘 다 실행됨, 아마도 변수 초기화 과정에서 꼬인듯');
      //setLikeState(true);
      //setLikeCount(likeCount + 1);

      dispatch(getPostAction({ userId, postId }));
      //console.log('dispatch 이후 redux store 상태가 바로 변경이 안 되므로 위처럼 다시 한 번 불러와야됨..', post);

      setLikeState(post.likeState);
      setLikeCount(post.likeCount);
    }
  }, [likePostError, likePostDone]);

  useUpdateEffect(() => {
    //싫어요 했으면
    if (likeDeleteDone) {
      dispatch(getPostAction({ userId, postId }));
      //console.log('요렇게는 바로 반영이 되는데..', post);
      setLikeState(post.likeState);
      setLikeCount(post.likeCount);
    }

    if (likeDeleteError) {
      //이렇게 하기보다 principal 체크를 하는 게 response data msg를 출력시키는 게 더 젛확하겠으나 귀찮으니 패스
      alert('로그인이 필요한 서비스입니다.');
    }
  }, [likeDeleteDone, likeDeleteError]);

  useUpdateEffect(() => {
    if (postDeleteDone) {
      alert('게시글 삭제에 성공하였습니다.');
      history.replace(`/${principal.id}`);
    }

    //좋아요 됐으면
    if (postDeleteError) {
      alert('게시글 삭제에 실패하였습니다.');
    }
  }, [postDeleteDone, postDeleteError]);

  const onLike = useCallback(() => {
    //console.log('like btn 클릭됨', postId);
    dispatch(likePostAction(postId));
  }, [dispatch, postId]);

  const onUnLike = useCallback(() => {
    console.log('unlike btn 클릭됨', postId);

    dispatch(likeDeleteAction(postId));
  }, [dispatch, postId]);

  const onDeletePost = useCallback(() => {
    console.log('게시글 삭제 클릭됨', postId);

    dispatch(postDeleteAction(postId));
  }, [dispatch, postId]);

  return (
    <>
      <div className="head-wrapper">
        <h1>{post.title}</h1>

        <StyledHeadDescDiv>
          <div className="information">
            <span className="username">
              <Link to={userId && `/${userId}`}>{post.user.username}</Link>
            </span>
            <span className="separator" style={{ marginLeft: '1rem' }}>
              ·
            </span>
            <span style={{ marginLeft: '1rem' }}>{moment(post.createDate).format('YYYY년 MM월 DD일')}</span>
          </div>

          {principal != null && userId == principal.id ? (
            <Button type="primary" shape="round" danger onClick={onDeletePost}>
              삭제
            </Button>
          ) : null}

          <div>
            {likeState ? (
              <StyledLikeBtn onClick={onUnLike}>
                <HeartFilled key="heart" />
                <span>{likeCount}</span>
              </StyledLikeBtn>
            ) : (
              <StyledUnlikeBtn onClick={onLike}>
                <HeartOutlined key="heart" />
                <span>{likeCount}</span>
              </StyledUnlikeBtn>
            )}
          </div>
        </StyledHeadDescDiv>
        <StyledPostDetailTagDiv>
          {post.tags.map((tag) => (
            <StyledDetailTagLink key={tag.id} to={`/tag?name=${tag.name}`}>
              {tag.name}
            </StyledDetailTagLink>
          ))}
        </StyledPostDetailTagDiv>
      </div>
    </>
  );
});

export default PostDetailHeader;
