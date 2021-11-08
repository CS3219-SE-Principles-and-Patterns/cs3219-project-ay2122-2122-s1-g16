import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import { loadState } from "../../../redux/localStateManagement";
import { Modal } from "../../component/Modal"
import { userCancelMatching, userGetInterviewQuestion, userRequestMatching } from "../../../redux/userSlice";
import { CountdownCircleTimer } from "react-countdown-circle-timer";
import {Img} from 'react-image'



export default function Dashboard() {
  const [difficultyLvlBtnShow, setDifficultyLvlBtnShow] = useState(false)
  const [isMatching, setIsMatching] = useState(false)
  const [isFetchingQuestion, setIsFetchingQuestion] = useState(false)
  const [isOpen, setIsOpen] = useState(false)
  const [isOpenWithRedirection, setIsOpenWithRedirection] = useState(false)
  const [modalTitle, setModalTitle] = useState("")
  const [modalMsg, setModalMsg] = useState("")
  const [countdown, setCountdown] = useState(30)
  const [readyToMatch, setReadyToMatch] = useState(false)
  const [difficulty, setDifficulty] = useState(false)
  const [user, setUser] = useState({})
  const [peerId, setPeerId] = useState("")
  const [interviewer, setInterviewer] = useState(false)
  const history = useHistory()

  useEffect(() => {
    async function loacUser() {
      setUser(await loadState('user'))
    } 
    loacUser()
  },[])

  useEffect(() => {
    if (user !== null && user !== {}) {
      setReadyToMatch(true)
    } else {
      history.push("/login")
      return 
    }
  },[user])

  useEffect(() => {
    console.log("peerId: " + peerId)
    if (isFetchingQuestion) {
      fetchQuestion(difficulty)
      setIsFetchingQuestion(false)
    }
  }, [peerId])

  window.onunload = () => {
    if (isMatching) {
      userCancelMatching(user.id, difficulty)
    }
  }

  const handleMatchingRequest = async (difficulty) => {
    setDifficulty(difficulty);
    if (readyToMatch) {
      setIsMatching(true)
      console.log(user)
      var response = await userRequestMatching(user.id, difficulty)
      if (response.data.message === "success") {
        var role = response.data.data.interviewer === 0 ? "interviewee" : "interviewer"
        setInterviewer(response.data.data.interviewer);
        setIsFetchingQuestion(true)
        setPeerId(response.data.data.peer_id)
        setModalTitle("Peer found!")
        setModalMsg("You will practice interview as an " + role + " first. Now we'll find the questions for you.")
        setIsOpen(true)
      } else {
        setModalTitle("Error")
        setModalMsg(response.data.message)
        setIsOpen(true)
      }
      setIsMatching(false)
    }
  }

  const handleCandleMatchingRequest = async () => {
    if (!isMatching) {
      setDifficultyLvlBtnShow(false)
      return 
    }
    var response = await userCancelMatching(user.id, difficulty)
    // console.log("in dashboard")
    console.log(response)
    if (response.message === "success") {
      // do nothing
    } else {
      console.log(response.message)
    }
    setIsMatching(false)
  }

  const fetchQuestion = async (difficultyLevel) => {
    setIsFetchingQuestion(true)
    console.log("peerId", peerId)
    var response = await userGetInterviewQuestion(user.id, peerId, difficultyLevel)
    if (response) {
      var role = interviewer === 0 ? "interviewee" : "interviewer"
      setModalTitle("Interview Start!")
      setModalMsg("You will practice interview as an " + role + " first. The questions are ready.")
      setIsOpenWithRedirection(true)
    } else {
      setModalTitle("Error")
      setModalMsg(response.message)
      setIsOpen(true)
    }
    setIsFetchingQuestion(false)
  }

  const CountDown = () => {
    const renderTime = ({ remainingTime }) => {
      if (remainingTime === 0) {
        if(isMatching) {
          userCancelMatching(user.id, difficulty)
          setModalTitle("Timeout")
          setModalMsg("Please try again later")
          setIsOpen(true)
          setIsMatching(false)
          setDifficultyLvlBtnShow(false)
          return <div className="timer">Time out</div>;
        }

        if(isFetchingQuestion) {
          setCountdown(30)
        }
      }
      return (
        <div className="timer">
          <div className="text text-center">{!isFetchingQuestion ? "Matching" : "Fetching"}</div>
          {!isFetchingQuestion ? <div className="value text-center">{remainingTime}</div> : null}
          <div className="text text-center">{!isFetchingQuestion ? "in progress" : "Question.."}</div>
        </div>
      );
    };
    
    return (
      <div className="timer-wrapper" style={{marginBottom:"112px", marginTop:"100px"}}>
        <CountdownCircleTimer
          isPlaying={true}
          duration={countdown}
          colors={[["#809E73", 0.33], ["#D7A8B2", 0.33], ["#B8335B"]]}
          onComplete={() => [true, 1000]}
        >
          {renderTime}
        </CountdownCircleTimer>
      </div>
    )
  }
   
  function DifficultyLvlBtn() {
    return difficultyLvlBtnShow ? (
      <div className="w-6/12 mx-auto px-4 flex justify-center backdrop-blur-md absolute bottom-0 mb-24">
        <button className="text-blueGray-400 bg-transparent border border-solid border-blueGray-400 hover:bg-blueGray-500 hover:text-white active:bg-blueGray-500  
                      font-bold uppercase text-l px-6 py-3 rounded shadow mr-8 
                      hover:shadow-lg outline-none focus:outline-none ease-linear transition-all duration-150" type="button" 
                      onClick={handleCandleMatchingRequest}>
           {isMatching ? "Cancel" : "Later"}
        </button>
        <button className="disabled:opacity-50 bg-emerald-500 text-white active:bg-blueGray-600 font-bold uppercase text-l  px-16 py-3 rounded shadow 
                      hover:shadow-lg outline-none focus:outline-none ease-linear transition-all duration-150" type="button"
                      onClick={() => {handleMatchingRequest(0)}}
                      disabled={isMatching}>
           Easy
        </button>
        <button className="disabled:opacity-50 bg-sky-400 text-white active:bg-blueGray-600 font-bold uppercase text-l  px-16 py-3 rounded shadow 
                      hover:shadow-lg outline-none focus:outline-none mx-6 ease-linear transition-all duration-150" type="button"
                      onClick={() => {handleMatchingRequest(1)}}
                      disabled={isMatching}>
          Medium
        </button>
        <button className="disabled:opacity-50 bg-purple-400 text-white active:bg-blueGray-600 font-bold uppercase text-l  px-16 py-3 rounded shadow 
                      hover:shadow-lg outline-none focus:outline-none ease-linear transition-all duration-150" type="button"
                      onClick={() => {handleMatchingRequest(2)}}
                      disabled={isMatching}>
           Hard
        </button>
      </div>
    ) : null;
  }

  function FindPeerBtn() {
    return !difficultyLvlBtnShow ? (
      <button className="bg-sky-500 w-10/12 text-white active:bg-sky-600 font-bold text-xl uppercase px-28 py-3 rounded shadow 
                    absolute bottom-0 mb-24
                    hover:shadow-lg outline-none focus:outline-none mx-8 ease-linear transition-all duration-150" type="button"
                    onClick={() => {setDifficultyLvlBtnShow(true)}}>
        <i className="fas fa-rocket"></i> &#160;&#160;Find a Peer !
      </button>
    ) : null;
  }
  
  return (
    <>
    <Modal title={modalTitle} msg={modalMsg}
            isOpen={isOpen} setIsOpen={setIsOpen}></Modal>
    <Modal title={modalTitle} msg={modalMsg}
            isOpen={isOpenWithRedirection} setIsOpen={setIsOpenWithRedirection} callBack={()=>{history.push('/interview')}}></Modal>
      <div className="relative flex flex-col mx-auto min-w-0 break-words mb-6 shadow-lg rounded-lg border-0 bg-no-repeat h-screen80 w-full"
        style={{ 
            minHeight: "780px",
            minWidth: "900px",
            backgroundColor:"rgb(233, 217, 200)"}}>
         <div className="h-full w-full justify-center flex">
          <div className="text-center mt-12">
              <h3 className="text-3xl font-semibold leading-normal mb-12 mt-4 text-blueGray-700" >
                Welcome to Peerprep
              </h3>
            </div>
              <div className="justify-center flex absolute bottom-0 rounded-t-lg shadow pb-32 pt-6" 
              style={{backgroundColor:"rgba(255, 255, 255, 0.8)", width:"800px", minHeight:"642px"}}>

                <div className="text-blueGray-600 mt-12 mb-12 mx-16 
                relative w-full justify-center flex">                 
                  {(!isMatching && !isFetchingQuestion) ? <Img 
                    src={require("../../../assets/img/dashboard_bg.png").default}
                    style={{width:"450px", height:"392px", }} ></Img> : 
                    <CountDown></CountDown>  
                  }             
                  <hr className="my-4 border-blueGray-300" />
                </div>
                
                <FindPeerBtn></FindPeerBtn>
                <DifficultyLvlBtn></DifficultyLvlBtn>

              </div>
          </div>
        </div>
    </>
  );
}
