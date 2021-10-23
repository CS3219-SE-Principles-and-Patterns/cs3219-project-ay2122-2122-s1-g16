import React from "react";
import { Link } from "react-router-dom";
import Navbar from "../../component/navbar/NavBar_unauth";
import Footer from "../../component/Footer";

export default function Register() {

  function RegisterBox() {
    const inputClassName = "border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150";
    return (
      <div className="container mx-auto px-4 h-full">
        <div className="flex content-center items-center justify-center h-full">
          <div className="w-full lg:w-6/12 px-4">
            <div className="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded-lg bg-blueGray-200 border-0">
              <div className="rounded-t mb-0 px-6 py-6">
                <div className="text-center mb-3 pb-6 pt-6">
                <h2 className="text-blueGray-700 text-3xl text-center mb-3 font-bold">
                    Welcome to Peerprep
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
                      Username
                    </label>
                    <input
                      type="email"
                      className={inputClassName}
                      placeholder="Userame"
                    />
                  </div>

                  <div className="relative w-full mb-5">
                    <label
                      className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                      htmlFor="grid-password"
                    >
                      Email
                    </label>
                    <input
                      type="email"
                      className={inputClassName}
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
                      className={inputClassName}
                      placeholder="Password"
                    />
                  </div>

                  <div className="relative w-full mb-5">
                    <label
                      className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                      htmlFor="grid-password"
                    >
                      Confirm Password
                    </label>
                    <input
                      type="password"
                      className={inputClassName}
                      placeholder="Confirm Password"
                    />
                  </div>

                 
                  <div className="text-center mt-12">
                    <button
                      className="bg-blueGray-800 text-white active:bg-blueGray-600 text-sm font-bold uppercase px-6 py-3 rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1 w-full ease-linear transition-all duration-150"
                      type="button"
                    >
                      Create Account
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    )
  }
  return (
    <>
    <Navbar showLoginBtn={true} showRigBtn={false}  />
      <section className="relative w-full h-full py-40 min-h-screen">
        <div
          className="absolute top-0 w-full h-full bg-blueGray-800 bg-no-repeat bg-full"
          style={{
            backgroundImage:
              "url(" + require("../../assets/img/auth_bg.png").default + ")",
          }}
        ></div>
        <RegisterBox></RegisterBox>
        <Footer position="absolute w-full bottom-0" bg="" />
      </section>
    </>
  );
}
