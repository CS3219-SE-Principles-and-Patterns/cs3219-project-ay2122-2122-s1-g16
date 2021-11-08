import React from 'react';
import ReactDOM from 'react-dom';
import { store } from './redux/store'
import { Provider } from 'react-redux'
import { BrowserRouter, Route, Switch, Redirect } from "react-router-dom";
import reportWebVitals from './reportWebVitals';
import MyRouter from './router/MyRoute'
import Landing from './view/pages/Landing'
import Login from './view/pages/Login'
import Register from './view/pages/Register'
import InterviewRouter from './router/InterviewRouter';
import '@fortawesome/fontawesome-free/css/all.min.css';
import 'assets/styles/tailwind.css';
import './index.css';

ReactDOM.render(
  <Provider store={store}>
    <BrowserRouter>
      <Switch>
        <Route path="/my" component={MyRouter} />
        <Route path="/interview" component={InterviewRouter} />
        <Route path="/resetPassword" exact component={Register}/>
        <Route path="/register" exact component={Register} />  
        <Route path="/login" exact component={Login} />
        <Route path="/" exact component={Landing} />
        <Redirect from="*" to="/" />
      </Switch>
    </BrowserRouter>
  </Provider>,
  document.getElementById('root')
);

reportWebVitals();
