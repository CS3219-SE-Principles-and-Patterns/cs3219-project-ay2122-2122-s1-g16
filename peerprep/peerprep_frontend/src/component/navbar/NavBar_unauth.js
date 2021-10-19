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
          className="text-white text-sm font-bold leading-relaxed inline-block mr-4 px-4 py-2 whitespace-nowrap uppercase 
                      hover:bg-blueGray-200 hover:shadow-md hover:outline-none focus:bg-blueGray-100"
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
          className="text-white text-sm font-bold leading-relaxed inline-block mr-4 px-4 py-2 whitespace-nowrap uppercase 
                      hover:bg-blueGray-200 hover:shadow-md hover:outline-none focus:bg-blueGray-100"
          to="/register" 
        >
        Register
        </Link>
      </li>
    ) : null;
  }
  return (
    <>
      <nav className="top-0 absolute z-50 w-full flex flex-wrap items-center justify-between px-2 py-3 navbar-expand-lg">
        <div className="container px-4 mx-auto flex flex-wrap items-center justify-between">
          <div className="w-full relative flex justify-between lg:w-auto lg:static lg:block lg:justify-start">
            <Link
              className="text-white text-sm font-bold leading-relaxed inline-block mr-4 px-4 py-2 whitespace-nowrap uppercase 
                          hover:bg-blueGray-200 hover:shadow-md hover:outline-none focus:bg-blueGray-100 mr-1 mb-1 ease-linear transition-all duration-150"
              to="/"
            >
              Peerprep T16
            </Link>
          </div>
          <div
            className={
              "lg:flex flex-grow items-center bg-white lg:bg-opacity-0 lg:shadow-none  block rounded shadow-lg"
            }
            id="example-navbar-warning"
          >
            <ul className="flex flex-col lg:flex-row list-none lg:ml-auto">
              <LoginBtn></LoginBtn>
              <RigBtn></RigBtn>
            </ul>
          </div>
        </div>
      </nav>
    </>
  );
}
