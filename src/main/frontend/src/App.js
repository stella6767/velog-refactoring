import 'antd/dist/antd.css';
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
      alert(cmRespDto.msg);
    }
  }, [logoutDone]);

  //404 에러페이지는 만들지 고민중
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(loadUserAction());
  }, []);

  return (
    <>
      <Switch>
        <Route path="/" exact={true} component={Home} />
        <Route path="/recent" exact={true} component={Recent} />
        <Route path="/write" exact={true} component={writeForm} />
        <Route path="/search" exact={true} component={Search} />
        <Route path="/setting" exact={true} component={Profile} />
        <Route path={`/tag`} exact={true} component={TagSearch} />
        {/* 왜 /tag 가 /:userId 라우트 밑에 있으면 오작동???? */}
        <Route path="/:userId" exact={true} component={User} />
        <Route path="/list/liked" exact={true} component={LikeList} />
        <Route path="/:userId/:postId" exact={true} component={PostDetail} />
        <Route path="/recent" exact={true} component={PostDetail} />
      </Switch>
    </>
  );
}

export default App;
