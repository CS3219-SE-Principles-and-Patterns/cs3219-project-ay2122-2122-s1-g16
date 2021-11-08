import React from "react";

export default function Footer(props) {
  const bg = props.bg;
  const position = props.position;
  let className = `${position} ${bg} pt-8 pb-2`;
  return (
    <>
      <footer className={className}>
        <div className="container mx-auto px-4">
          <hr className="my-4 border-blueGray-300" />
          <div className="flex flex-wrap items-center md:justify-between justify-center">
            <div className="w-full md:w-4/12 px-4 mx-auto text-center">
              <div className="text-sm text-blueGray-500 font-semibold"
              style={{minWidth:"300px"}}>
                Copyright © {new Date().getFullYear()} CS3219 By{" "}
                <a
                  href="https://github.com/CS3219-SE-Principles-and-Patterns/cs3219-project-ay2122-2122-s1-g16"
                  className="text-blueGray-500 hover:text-blueGray-800"
                >
                  Team 16
                </a>
                .
              </div>
            </div>
          </div>
        </div>
      </footer>
    </>
  );
}
