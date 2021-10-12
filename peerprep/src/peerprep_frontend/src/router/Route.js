import React from "react";
import { Switch, Route, Redirect } from "react-router-dom";

import Sidebar from "../component/navbar/SideBar";
import UserBanner from "../component/UserBanner";
import Footer from "../component/Footer";

import Dashboard from "../view/loggedIn/Dashboard";
import Profile from "../view/loggedIn/user/Profile";
import History from "../view/loggedIn/user/History";
import ContactUs from "../view/ContactUs";

export default function Admin() {
  return (
    <>
      <Sidebar />
      <div className="relative md:ml-64 bg-blueGray-100">
        <UserBanner />
        <div className="px-4 md:px-10 mx-auto w-full -m-24">
          <Switch>
            <Route path="/my/dashboard" exact component={Dashboard} />
            <Route path="/my/profile" exact component={Profile} />
            <Route path="/my/history" exact component={History} />
            <Route path="/my/contactUs" exact component={ContactUs} />
            <Redirect from="/my" to="/my/dashboard" />
          </Switch>
          <Footer bg="" position="relative"/>
        </div> 
      </div>
    </>
  );
}
