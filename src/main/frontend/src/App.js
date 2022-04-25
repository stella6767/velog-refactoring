import 'antd/dist/antd.css';
import UpdateForm from 'pages/post/UpdateForm';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useSelector } from 'react-redux';
import { Route } from 'react-router';
import { Switch } from 'react-router-dom';
import './App.css';
import useUpdateEffect from './lib/hooks/useUpdateEffect';
import Home from './pages/home/Home';
import PostDetail from './pages/post/PostDetail';
import writeForm from './pages/post/writeForm';
import Profile from './pages/profile/Profile';
import Recent from './pages/recent/Recent';
import Search from './pages/search/Search';
import TagSearch from './pages/search/TagSearch';
import LikeList from './pages/user/LikeList';
import User from './pages/user/User';
import { loadUserAction } from './reducers/auth';

function App() {
  const { cmRespDto, logoutDone } = useSelector(({ auth }) => ({
    cmRespDto: auth.cmRespDto,
    logoutDone: auth.logoutDone,
  }));

  useUpdateEffect(() => {
    if (logoutDone) {
      alert('로그아웃 되었습니다.');
    }
  }, [logoutDone]);

  //404 에러페이지는 만들지 고민중
  const dispatch = useDispatch();

  useEffect(() => {
    //dispatch(loadUserAction());

    fetch('/hello')
      .then((response) => {
        return response.json();
      })
      .then(function (data) {
        alert(data);
      });
  }, []);

  return (
    <>
      <Switch>
        <Route path="/" exact={true} component={Home} />
        <Route path="/recent" exact={true} component={Recent} />
        <Route path="/write" exact={true} component={writeForm} />
        {/* <Route path="/update" exact={true} component={UpdateForm} /> */}
        <Route path="/search" exact={true} component={Search} />
        <Route path="/setting" exact={true} component={Profile} />
        <Route path={`/tag`} exact={true} component={TagSearch} />
        {/* 왜 /tag 가 /:userId 라우트 밑에 있으면 오작동???? */}
        <Route path="/:userId" exact={true} component={User} />
        <Route path="/list/liked" exact={true} component={LikeList} />
        <Route path="/:userId/:postId" exact={true} component={PostDetail} />
        <Route path="/:userId/update/:postId" exact={true} component={UpdateForm} />
      </Switch>
    </>
  );
}

export default App;
