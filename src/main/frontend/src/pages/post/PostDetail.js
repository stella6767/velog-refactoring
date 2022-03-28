import React, { memo, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import AppHeader from '../../components/AppHeader';
import PostDetailComment from '../../components/PostDetailComment';
import PostDetailHeader from '../../components/PostDetailHeader';
import { getPostAction } from '../../reducers/post';
import { Global, StyledDetailContentDiv, StyledPostDetailContainer } from '../user/style';

const PostDetail = memo((props) => {
  const { post, getPostDone, principal, postDeleteDone, postDeleteError } = useSelector(({ post, auth }) => ({
    post: post.post,
    getPostDone: post.getPostDone,
    postDeleteDone: post.postDeleteDone,
    postDeleteError: post.postDeleteError,
    principal: auth.principal,
  }));

  const dispatch = useDispatch();

  useEffect(() => {
    const postId = props.match.params.postId;
    const userId = props.match.params.userId;
    dispatch(getPostAction({ userId, postId })); //Redux store 값이랑 동기화가 한발 늦게 되네..
  }, []);

  return (
    <>
      {post && (
        <>
          <Global />
          <AppHeader />
          <StyledPostDetailContainer>
            <PostDetailHeader
              principal={principal}
              postDeleteDone={postDeleteDone}
              postDeleteError={postDeleteError}
              userId={props.match.params.userId}
              postId={props.match.params.postId}
            />
            {post.content && <StyledDetailContentDiv dangerouslySetInnerHTML={{ __html: post.content }} />}
            <PostDetailComment
              post={post}
              userId={props.match.params.userId}
              postId={props.match.params.postId}
              getPostDone={getPostDone}
            />
          </StyledPostDetailContainer>
        </>
      )}
    </>
  );
});

export default PostDetail;
