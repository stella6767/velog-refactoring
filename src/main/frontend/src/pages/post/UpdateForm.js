import React, { memo, useEffect } from 'react';
import { useState } from 'react';
import TextEditor from '../../components/TextEditor';
import styled from 'styled-components';
import { Global, StyledButtonDiv } from './style';
import { Input, Form, Button } from 'antd';
import { useDispatch } from 'react-redux';
import { addPostAction, getPostAction, updatePostAction } from '../../reducers/post';
import useUpdateEffect from '../../lib/hooks/useUpdateEffect';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';

const StyledPostDiv = styled.div`
  padding: 2rem;
`;

const UpdateForm = memo((props) => {
  const { updatePostDone, principal, postId, post, getPostDone } = useSelector(({ post, auth }) => ({
    updatePostDone: post?.updatePostDone,
    principal: auth?.principal,
    postId: post?.addPostId,
    post: post?.post,
    getPostDone: post?.getPostDone,
  }));

  const { params } = props.match;
  const [value, setvalue] = useState('');
  const [form] = Form.useForm();
  const dispatch = useDispatch();

  useEffect(() => {
    //console.log('???');
    const postId = props.match.params.postId;
    const userId = props.match.params.userId;
    dispatch(getPostAction({ userId, postId }));
  }, []);

  useUpdateEffect(() => {
    if (updatePostDone) {
      //history go postdetail
      console.log('수정하기 성공');
      props.history.replace(`/${principal.id}/${params.postId}`);
    }
  }, [updatePostDone, postId]);

  useUpdateEffect(() => {
    console.log('post ', post);
  }, [getPostDone]);

  const onPostFinish = (values) => {
    console.log('post 수정 제출', values, params.postId);

    let postId = params.postId;

    if (!values.tags.length) {
      console.log('확인1', values.tags);

      values.tags = '';
    }

    dispatch(updatePostAction(postId, values));
  };

  // const exitForm = useCallback(() =>{
  // },[]);

  const exitForm = () => {
    // console.log('props', props);
    // console.log('history객체', props.history);
    // console.log('match 객체', props.match);
    // console.log('location 객체', props.location);

    props.history.goBack();
  };

  return (
    <>
      <StyledPostDiv>
        <Global />
        {getPostDone && (
          <Form form={form} onFinish={onPostFinish}>
            {/* <input type="text" className="form-control" placeholder="제목을 입력하세요" name="title" /> */}
            <div className="titleDiv">
              <Form.Item
                name="title"
                rules={[
                  {
                    required: true,
                  },
                ]}
                initialValue={post?.title}
              >
                <Input placeholder="제목을 입력하세요" />
              </Form.Item>
            </div>
            <Form.Item name="tags" initialValue={post?.tags}>
              <Input placeholder="#태그는 수정 시 새로 입력해주셔야 됩니다. 제가 그정도까지 신경을 쓰진 않아서" />
            </Form.Item>

            <Form.Item name="content">
              {/* {!post?.content ? (
                <TextEditor name="content" value={post?.content} onChange={(value) => setvalue(value)}></TextEditor>
              ) : null} */}
              <TextEditor name="content" value={value} onChange={(value) => setvalue(value)}></TextEditor>
            </Form.Item>
            <Form.Item>
              <StyledButtonDiv>
                <Button onClick={exitForm}>뒤로가기</Button>
                <Button type="primary" htmlType="submit">
                  수정하기
                </Button>
              </StyledButtonDiv>
            </Form.Item>
          </Form>
        )}
      </StyledPostDiv>
    </>
  );
});

export default UpdateForm;
