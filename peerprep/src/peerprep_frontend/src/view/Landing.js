import React from "react";

import Navbar from "component/navbar/NavBar_unauth";
import Footer from "component/Footer";

export default function Landing() {
  return (
    <>
      <Navbar showLoginBtn={true} showRigBtn={true}  />
      <main>
        <div className="relative pt-16 pb-32 flex content-center items-center justify-center min-h-screen-75">
          <div
            className="absolute top-0 w-full h-full bg-center bg-cover"

            style={{
              backgroundImage:
                "url(" + require("../assets/img/landing_bg2.jpeg").default + ")",
            }}
          >
            <span
              id="blackOverlay"
              className="w-full h-full absolute opacity-75 bg-black"></span>
          </div>
          <div className="container relative mx-auto">
            <div className="items-center flex flex-wrap">
              <div className="w-full lg:w-8/12 px-4 ml-auto mr-auto text-center">
                <div className="pr-12">
                  <h1 className="text-white font-semibold text-5xl">
                    Your career start with Peerprep.
                  </h1>
                  <p className="mt-4 text-lg text-blueGray-200">
                    Peerprep is an interview preparation platform which provide a peer matching system. 
                    You can find peers to practice whiteboard style interview questions together.
                  </p>
                </div>
              </div>
            </div>
          </div>
          <div
            className="top-auto bottom-0 left-0 right-0 w-full absolute pointer-events-none overflow-hidden h-70-px"
            style={{ transform: "translateZ(0)" }}
          >
            <svg
              className="absolute bottom-0 overflow-hidden"
              xmlns="http://www.w3.org/2000/svg"
              preserveAspectRatio="none"
              version="1.1"
              viewBox="0 0 2560 100"
              x="0"
              y="0"
            >
              <polygon
                className="text-blueGray-200 fill-current"
                points="2560 0 2560 100 0 100"
              ></polygon>
            </svg>
          </div>
        </div>

        <section className="pb-20 bg-blueGray-200 -mt-24">
          <div className="container mx-auto px-4">
            <div className="flex flex-wrap">
              <div className="lg:pt-12 pt-6 w-full md:w-4/12 px-4 text-center">
                <div className="relative flex flex-col min-w-0 break-words bg-white w-full mb-8 shadow-lg rounded-lg">
                  <div className="px-4 py-5 flex-auto">
                    <div className="text-white p-3 text-center inline-flex items-center justify-center w-12 h-12 mb-5 shadow-lg rounded-full bg-amber-400">
                      <i className="fas fa-database"></i>
                    </div>
                    <h6 className="text-xl font-semibold">Rich Question Bank</h6>
                    <p className="mt-2 mb-4 text-blueGray-500">
                      Peerpre has a powerful question bank. It provides tech-questions with different difficulty levels, Easy, Medium and Hard.
                    </p>
                  </div>
                </div>
              </div>

              <div className="w-full md:w-4/12 px-4 text-center">
                <div className="relative flex flex-col min-w-0 break-words bg-white w-full mb-8 shadow-lg rounded-lg">
                  <div className="px-4 py-5 flex-auto">
                    <div className="text-white p-3 text-center inline-flex items-center justify-center w-12 h-12 mb-5 shadow-lg rounded-full bg-lightBlue-400">
                      <i className="fas fa-chalkboard-teacher"></i>
                    </div>
                    <h6 className="text-xl font-semibold">Technical Interview Practice</h6>
                    <p className="mt-2 mb-4 text-blueGray-500">
                      In Peerprep, you can always find a partner to practice the tech-interview. 
                      Both of you will have a chance to play a interviewer and interviewee role.
                    </p>
                  </div>
                </div>
              </div>

              <div className="pt-6 w-full md:w-4/12 px-4 text-center">
                <div className="relative flex flex-col min-w-0 break-words bg-white w-full mb-8 shadow-lg rounded-lg">
                  <div className="px-4 py-5 flex-auto">
                    <div className="text-white p-3 text-center inline-flex items-center justify-center w-12 h-12 mb-5 shadow-lg rounded-full bg-emerald-400">
                      <i className="fas fa-history"></i>
                    </div>
                    <h6 className="text-xl font-semibold">History Tracking</h6>
                    <p className="mt-2 mb-4 text-blueGray-500">
                      Peerprep stores the details of questions you encountered before. 
                      You can always find the its description, result and your approaches.
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>
      </main>
      <Footer bg="bg-blueGray-200" position="relative" />
    </>
  );
}
