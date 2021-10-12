import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Route, Switch, Redirect } from "react-router-dom";
import './index.css';
import reportWebVitals from './reportWebVitals';
import "@fortawesome/fontawesome-free/css/all.min.css";
import "assets/styles/tailwind.css";

import Landing from './view/Landing'
import Login from './view/auth/Login'
import Register from './view/auth/Register'




ReactDOM.render(
  <BrowserRouter>
    <Switch>
      <Route path="/register" exact component={Register} />  
      <Route path="/login" exact component={Login} />
      <Route path="/" exact component={Landing} />
      <Redirect from="*" to="/" />
    </Switch>
  </BrowserRouter>,
  document.getElementById('root')
);

reportWebVitals();
