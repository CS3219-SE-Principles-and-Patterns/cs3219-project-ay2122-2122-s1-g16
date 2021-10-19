import React from "react";

// components


export default function Dashboard() {
  const [difficultyLvlBtnShow, setDifficultyLvlBtnShow] = React.useState(false);
  
  function DifficultyLvlBtn() {
    return difficultyLvlBtnShow ? (
      <div class="block" className=" w-6/12 mx-auto px-4 flex justify-center backdrop-blur-md absolute bottom-0 mb-24">
        <button class="text-blueGray-400 bg-transparent border border-solid border-blueGray-400 hover:bg-blueGray-500 hover:text-white active:bg-blueGray-500  
                      font-bold uppercase text-l px-6 py-3 rounded shadow mr-8 
                      hover:shadow-lg outline-none focus:outline-none ease-linear transition-all duration-150" type="button" onClick={() => {setDifficultyLvlBtnShow(false)}}>
           Later
        </button>
        <button class="bg-emerald-500 text-white active:bg-blueGray-600 font-bold uppercase text-l  px-16 py-3 rounded shadow 
                      hover:shadow-lg outline-none focus:outline-none ease-linear transition-all duration-150" type="button">
           Easy
        </button>
        <button class="bg-sky-400 text-white active:bg-blueGray-600 font-bold uppercase text-l  px-16 py-3 rounded shadow 
                      hover:shadow-lg outline-none focus:outline-none mx-6 ease-linear transition-all duration-150" type="button">
          Medium
        </button>
        <button class="bg-purple-400 text-white active:bg-blueGray-600 font-bold uppercase text-l  px-16 py-3 rounded shadow 
                      hover:shadow-lg outline-none focus:outline-none ease-linear transition-all duration-150" type="button">
           Hard
        </button>
      </div>
    ) : null;
  }

  function FindPeerBtn() {
    return !difficultyLvlBtnShow ? (
      <button class="bg-sky-500 w-10/12 text-white active:bg-sky-600 font-bold text-xl uppercase px-28 py-3 rounded shadow 
                    absolute bottom-0 mb-24
                    hover:shadow-lg outline-none focus:outline-none mx-8 ease-linear transition-all duration-150" type="button"
                    onClick={() => {setDifficultyLvlBtnShow(true)}}>
        <i class="fas fa-rocket"></i> &#160;&#160;Find a Peer !
      </button>
    ) : null;
  }
  
  return (
    <>
      <div className="relative flex flex-col mx-auto min-w-0 break-words mb-6 shadow-lg rounded-lg bg-blueGray-100 border-0 bg-no-repeat h-screen80 w-full"
        style={{ 
            minWidth: "900px",
            backgroundSize:"cover",
            backgroundImage: "url(" + require("../../assets/img/dashboard_bg2.png").default + ")"}}>
         <div className="h-full w-full justify-center flex">
          
          <div className="text-center mt-12">
              <h3 className="text-3xl font-semibold leading-normal mb-12 mt-4 text-blueGray-700" >
                Welcome to Peerprep
              </h3>
            </div>
              <div class="justify-center flex absolute bottom-0 rounded-t-lg shadow pb-56" style={{backgroundColor:"rgba(255, 255, 255, 0.8)", width:"800px"}}>

                <div className="text-blueGray-600 mt-12 mb-12 mx-16">
                  Choose your desire difficulty level and find a peer by clicking below button! 
                  <p>
                  Lorem ipsum, or lipsum as it is sometimes known, is dummy text used in laying out print, 
                  graphic or web designs. The passage is attributed to an unknown typesetter in the 15th century who is thought to have scrambled parts of 
                  Cicero's De Finibus Bonorum et Malorum for use in a type specimen book.
                  </p>
                  <br/>
                  <p>
                  Choose your desire difficulty level and find a peer by clicking below button! 
                  Lorem ipsum, or lipsum as it is sometimes known, is dummy text used in laying out print, 
                  graphic or web designs. The passage is attributed to an unknown typesetter in the 15th century who is thought to have scrambled parts of 
                  Cicero's De Finibus Bonorum et Malorum for use in a type specimen book.
                  </p>
                  <br/>
                  <p>
                  Choose your desire difficulty level and find a peer by clicking below button! 
                  Lorem ipsum, or lipsum as it is sometimes known, is dummy text used in laying out print.
                  </p>
                </div>
                <FindPeerBtn></FindPeerBtn>
                <DifficultyLvlBtn></DifficultyLvlBtn>

              </div>
          </div>
        </div>
    </>
  );
}
