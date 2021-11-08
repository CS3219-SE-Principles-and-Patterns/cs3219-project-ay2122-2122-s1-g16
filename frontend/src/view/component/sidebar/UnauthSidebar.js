/*eslint-disable*/
import React from "react";
import { Link } from "react-router-dom";

export default function Navbar(props) {
  const showAuthBtn = props.showAuthBtn;
  const showLoginBtn = props.showLoginBtn;
  const showRigBtn = props.showRigBtn;

  function LoginBtn() {
    return showLoginBtn ? (
      <li className="flex items-center">
        <Link
          className="text-white text-sm font-bold leading-relaxed inline-block mr-4 px-4 py-2 whitespace-nowrap uppercase rounded-lg
                      hover:bg-blueGray-500 hover:shadow-md hover:outline-none focus:bg-blueGray-300
                      ease-linear transition-all duration-50"
          to="/login" 
        >
        Login
        </Link>
      </li>
    ) : null;
  }

  function RigBtn() {
    return showRigBtn ? (
      <li className="flex items-center">
        <Link
          className="text-white text-sm font-bold leading-relaxed inline-block mr-4 px-4 py-2 whitespace-nowrap uppercase rounded-lg
                      hover:bg-blueGray-500 hover:shadow-md hover:outline-none focus:bg-blueGray-300
                      ease-linear transition-all duration-50"
          to="/register" 
        >
        Register
        </Link>
      </li>
    ) : null;
  }
  return (
    <>
      <nav className="top-0 absolute z-50 w-full flex flex-wrap items-center justify-between px-2 py-3 navbar-expand-lg" style={{minWidth:"400px"}}>
        <div className="container px-4 mx-auto flex flex-wrap items-center justify-between">
          <div className="w-auto static block justify-start">
            <Link
              className="text-white text-sm font-bold leading-relaxed inline-block mr-4 px-4 py-2 whitespace-nowrap uppercase 
                          hover:bg-blueGray-500 hover:shadow-md hover:outline-none focus:bg-blueGray-300 mb-1 rounded-lg
                          ease-linear transition-all duration-50"
              to="/"
            >
              Peerprep T16
            </Link>
          </div>
          <div className="flex flex-grow items-center bg-opacity-0 shadow-none rounded">
            <ul className="flex flex-row list-none ml-auto">
              <LoginBtn></LoginBtn>
              <RigBtn></RigBtn>
            </ul>
          </div>
        </div>
      </nav>
    </>
  );
}
