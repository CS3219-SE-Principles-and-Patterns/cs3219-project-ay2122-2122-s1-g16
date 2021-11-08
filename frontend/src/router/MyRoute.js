import React from "react";
import { Switch, Route, Redirect } from "react-router-dom";
import Sidebar from "../view/component/sidebar/AuthSideBar";
import Footer from "../view/component/Footer";
import Dashboard from "../view/pages/authenticated/Dashboard";
import Profile from "../view/pages/authenticated/Profile";
import History from "../view/pages/authenticated/History";

export default function MyRouter() {
  return (
    <>
      <Sidebar />
      <div className="relative h-screen" 
        style={{minWidth: "960px", 
        backgroundSize:"cover",
        backgroundImage: "url(" + require("../assets/img/landing_bg.jpeg").default + ")"}}>
        <div style={{backgroundColor:"rgba(0, 0, 0, 0.7)"}}>
          <div className="relative md:ml-48 py-4 md:pt-16 pt-12" 
              style={{minWidth: "960px"}}>
              <div className="px-4 md:px-10 w-full" >
                <Switch>
                  <Route path="/my/dashboard" exact component={Dashboard}/>
                  <Route path="/my/profile" exact component={Profile}/>
                  <Route path="/my/history" exact component={History}/>
                  <Redirect from="/my" to="/my/dashboard" />
                </Switch>
                <Footer position="relative w-full bottom-0" bg=""/>
              </div> 
            </div>
          </div>
        </div>
    </>
  );
}
