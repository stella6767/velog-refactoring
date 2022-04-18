import useUpdateEffect from 'lib/hooks/useUpdateEffect';
import React, { useCallback, useEffect, useRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import AppLayout from '../../components/AppLayout';
import { notImpl } from '../../lib/constants/auth';
import { imgPutAction } from '../../reducers/user';
import {
  StyledImgRemoveButton,
  StyledImgUploadButton,
  StyledProfileImg,
  StyledSubDetailDiv,
  StyledUserDescBottomSection,
  StyledUserDescTopSection,
  StyledUserInfoDiv,
  StyledUserThubnailDiv,
} from './style';
import './style.css';

const Profile = () => {
  const { principal, uploadImgDone } = useSelector(({ auth, user }) => ({
    principal: auth?.principal,
    uploadImgDone: user?.uploadImgDone,
  }));

  // const [imgFile, setImgFile] = useState();
  // const [imgEvent, setImgEvent] = useState();

  const dispatch = useDispatch();

  useUpdateEffect(() => {
    if (uploadImgDone) {
      //기본적으로 새로고침하면 state 값 상실,
      console.log('확인', principal?.picture);

      location.reload();
    }
    //console.log('profileImg', profileImg);
  }, [uploadImgDone]);

  const imageInput = useRef();

  const userImg = useRef();

  const onClickImageUpload = useCallback(() => {
    imageInput.current.click();
  }, [imageInput.current]);

  const onChangeImages = useCallback((e, userId) => {
    //console.log('images', e.target.files);
    //console.log('principalId', userId);
    let files = e.target.files;
    let filesArr = Array.prototype.slice.call(files);

    const data = new FormData();

    filesArr.forEach((f) => {
      if (!f.type.match('image.*')) {
        alert('이미지를 등록해야 합니다.');
        return;
      }
      //let profileImageForm = document.querySelector('#profile-image_form');
      //console.log('profileimg', profileImageForm);
      data.append('profileImageFile', f);

      //setImgFile(f);
      //console.log('event', e.target.result); //없네??
      //setImgEvent(e.target.result);

      console.log('imageFormData', data);
      dispatch(imgPutAction({ userId, data }));
    });

    // [].forEach.call(e.target.files, (f) => {
    //   data.append('image', f);
    //   console.log('f', f);
    // });
    // console.log(data);
    // //객체로 받을 때는 변수명 일치 신경쓰셈

    //dispatch(imgPutAction({ userId, data }));
  }, []);

  return (
    <>
      <AppLayout>
        {principal && (
          <StyledUserDescTopSection>
            <StyledUserThubnailDiv>
              <StyledProfileImg src={principal?.picture} alt="" ref={userImg} />

              <input
                type="file"
                name="profileImageFile"
                multiple
                hidden
                ref={imageInput}
                onChange={(e) => onChangeImages(e, principal.id)}
              />

              <StyledImgUploadButton onClick={onClickImageUpload}>이미지 업로드</StyledImgUploadButton>
              <StyledImgRemoveButton onClick={notImpl}>이미지 제거</StyledImgRemoveButton>
            </StyledUserThubnailDiv>
            <StyledUserInfoDiv>
              <h2>{principal?.username}</h2>
              <p>소셜 정보 및 로그인, 이메일 주소, 회원 탈퇴, 프로필 사진 업로드 등은 차후 구현예정? </p>
              {/* <button className="sc-fcdeBU eZBjgD">수정</button> */}
            </StyledUserInfoDiv>
          </StyledUserDescTopSection>
        )}

        <StyledUserDescBottomSection>
          <StyledSubDetailDiv>
            <div className="wrapper">
              <div className="title-wrapper">
                <h3>벨로그 제목</h3>
              </div>
              <div className="block-for-mobile">
                <div className="contents">{principal?.username}.log</div>
                <div className="edit-wrapper">
                  <button className="updateBtn">수정</button>
                </div>
              </div>
            </div>
          </StyledSubDetailDiv>
          <StyledSubDetailDiv>
            <div className="wrapper">
              <div className="title-wrapper">
                <h3>제작자 소셜 정보</h3>
              </div>
              <div className="block-for-mobile">
                <div className="contents">
                  <ul className="sc-fZwumE gvdRTK">
                    <li>
                      <span>https://github.com/stella6767</span>
                    </li>
                    <li>
                      <span>https://blog.naver.com/alsrb9434</span>
                    </li>
                    <li>
                      <span>https://velog.io/@stella6767</span>
                    </li>
                  </ul>
                </div>
                <div className="edit-wrapper">
                  <button className="updateBtn">수정</button>
                </div>
              </div>
            </div>
          </StyledSubDetailDiv>
          <StyledSubDetailDiv>
            <div className="wrapper">
              <div className="title-wrapper">
                <h3>이메일 주소</h3>
              </div>
              <div className="block-for-mobile"></div>
            </div>
            <div className="description">회원 인증 또는 시스템에서 발송하는 이메일을 수신하는 주소입니다.</div>
          </StyledSubDetailDiv>
          <StyledSubDetailDiv>
            <div className="wrapper">
              <div className="title-wrapper">
                <h3>회원 탈퇴</h3>
              </div>
              <div className="block-for-mobile">
                <button color="red" className="sc-dnqmqq dGwAmB">
                  회원 탈퇴
                </button>
              </div>
            </div>
          </StyledSubDetailDiv>
        </StyledUserDescBottomSection>
      </AppLayout>
    </>
  );
};

export default Profile;
