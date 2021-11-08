import React from "react";
import { Switch, Route, Redirect } from "react-router-dom";
import InterviewDashboard from "../view/pages/authenticated/InterviewDashboard";

export default function InterviewRouter() {
  return (
    <>
      <div className="relative h-screen" 
        style={{minWidth: "960px"}}>
        <div className="h-screen" style={{backgroundColor:"rgba(0, 0, 0, 0.7)"}}>
          <div className="relative ml-20 pt-9" 
              style={{minWidth: "960px"}}>
              <div className="px-10 w-full" >
                <Switch>
                  <Route path="/interview" exact component={InterviewDashboard}/>
                  <Redirect from="/my" to="/my/dashboard" />
                </Switch>
              </div> 
            </div>
          </div>
        </div>
    </>
  );
}
