import moment from 'moment';
import React, { memo } from 'react';
import { Link } from 'react-router-dom';
import { StyledDetailTagLink, StyledPostBoxDiv, StyledPostContentP } from './style';

//트렌딩 페이지나 최신 페이지가 아닐 경우
const PostBox = memo((props) => {
  const { post, userId } = props;

  return (
    <>
      {post && (
        <Link to={`/${userId}/${post.id}`}>
          <StyledPostBoxDiv>
            <h2>{post.title}</h2>
            <StyledPostContentP dangerouslySetInnerHTML={{ __html: post.content.substr(0, 200) }} />
            <div className="tags-wrapper">
              {post.tags.map((tag) => (
                <StyledDetailTagLink key={tag.id} to={`/tag?name=${tag.name}`}>
                  {tag.name}
                </StyledDetailTagLink>
              ))}
            </div>
            <div className="subinfo">
              <span>{moment(post.createDate).format('YYYY년 MM월 DD일')}</span>
              <div className="separator">·</div>
              <span>{post.comments.length}개의 댓글</span>
            </div>
          </StyledPostBoxDiv>
        </Link>
      )}
    </>
  );
});

export default PostBox;
