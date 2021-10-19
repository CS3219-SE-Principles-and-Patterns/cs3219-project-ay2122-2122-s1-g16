import React from "react";
import { Link } from "react-router-dom";
import Navbar from "../../component/navbar/NavBar_unauth";
import Footer from "../../component/Footer";

export default function Login() {

  function LoginBox() {
    return (
      <div className="container mx-auto px-4 h-full">
        <div className="flex content-center items-center justify-center h-full">
          <div className="w-full lg:w-6/12 px-4">
            <div className="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded-lg bg-blueGray-200 border-0">
              <div className="rounded-t mb-0 px-6 py-6">
                <div className="text-center mb-3 pb-6 pt-6">
                  <h2 className="text-blueGray-700 text-3xl text-center mb-3 font-bold">
                    Welcome back to Peerprep
                  </h2>
                </div>
                <hr className="mt-6 border-b-1 border-blueGray-300" />
              </div>
              <div className="flex-auto px-4 lg:px-10 py-10 pt-0">
                <form>
                  <div className="relative w-full mb-5">
                    <label
                      className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                      htmlFor="grid-password"
                    >
                      Email
                    </label>
                    <input
                      type="email"
                      className="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                      placeholder="Email"
                    />
                  </div>

                  <div className="relative w-full mb-5">
                    <label
                      className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                      htmlFor="grid-password"
                    >
                      Password
                    </label>
                    <input
                      type="password"
                      className="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                      placeholder="Password"
                    />
                  </div>
                  <div>
                    <label className="inline-flex items-center cursor-pointer">
                      <input
                        id="customCheckLogin"
                        type="checkbox"
                        className="form-checkbox border-0 rounded text-blueGray-700 ml-1 w-5 h-5 ease-linear transition-all duration-150"
                      />
                      <span className="ml-2 text-sm font-semibold text-blueGray-600">
                        Remember me
                      </span>
                    </label>
                  </div>

                  <div className="text-center mt-12">
                    <button
                      className="bg-blueGray-800 text-white active:bg-blueGray-600 text-sm font-bold uppercase px-6 py-3 rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1 w-full ease-linear transition-all duration-150"
                      type="button"
                    >
                      Sign In
                    </button>
                  </div>
                </form>
              </div>
            </div>
            <div className="flex flex-wrap mt-6 relative">
              <div className="w-full text-right">
                <a
                  href="#pablo"
                  onClick={(e) => e.preventDefault()}
                  className="text-blueGray-200
                  hover:bg-blueGray-200 hover:shadow-md hover:outline-none focus:bg-blueGray-100 mr-1 mb-1 ease-linear transition-all duration-50"
                >
                  <small>Forgot password?</small>
                </a>
              </div>
              
            </div>
          </div>
        </div>
      </div>
    )
  }
  return (
    <>
    <Navbar showLoginBtn={false} showRigBtn={true} />
    <section className="relative w-full h-full py-40 min-h-screen">
      <div
        className="absolute top-0 w-full h-full bg-blueGray-800 bg-no-repeat bg-full"
        style={{
          backgroundImage:
            "url(" + require("../../assets/img/auth_bg.png").default + ")",
        }}
      ></div>
      <LoginBox></LoginBox>
      <Footer position="absolute w-full bottom-0" bg="" />
    </section>
    </>
  );
}
