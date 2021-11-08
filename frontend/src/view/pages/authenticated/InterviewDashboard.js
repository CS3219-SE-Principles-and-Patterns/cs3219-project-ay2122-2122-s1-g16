import React, { useEffect, useState } from 'react';
import { useHistory } from "react-router-dom";
import useWebSocket from 'react-use-websocket';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { loadState } from "../../../redux/localStateManagement";
import { CustomDisclosure } from '../../component/Disclosure';
import InterviewSidebar from '../../component/sidebar/InterviewSidebar';
import { Modal } from '../../component/Modal';
import { userChangeRole, userLeaveInterview } from '../../../redux/userSlice'
import { parseRole, isJson } from '../../../util/util';


export default function InterviewDashboard() {
  const [readyToStart, setReadyToStart] = useState(false)
  const [role, setRole] = useState("")
  const [userId, setUserId] = useState("")
  const [peerId, setPeerId] = useState("")
  const [myQuestion, setMyQuestion] = useState("")
  const [peerQuestion, setPeerQuestion] = useState("")

  const [myInputAsInterviewee, setmyInputAsInterviewee] = useState("")
  const [myInputAsInterviewer, setmyInputAsInterviewer] = useState("")

  const [peerInput, setPeerInput] = useState("")
  const [shouldConnectWss, setShouldConnectWss] = useState(true)
  const [count, setCount] = useState(0)

  const [modalTitle, setModalTitle] = useState("")
  const [modalMsg, setModalMsg] = useState("")
  const [isOpen, setIsOpen] = useState(false) 
  const [isOpenForSwitchingRole, setIsOpenForSwitchingRole] = useState(false)
  const [isOpenForLeavingInterview, setIsOpenForLeavingInterview] = useState(false)


  const [editingUrl, setEditingUrl] = useState("wss://peerprep-backend-service-mepxozmoha-as.a.run.app/interview/")
  // const [editingUrl, setEditingUrl] = useState("ws://localhost:8080/interview/")

  const history = useHistory()

  useEffect(() => {
    async function loadUser() {
      if (await loadState('user') === null || await loadState('role') === null) {
        history.push("/login")
        return 
      }
      loadFromState()
    }
  loadUser()
  },[])

  const loadFromState = async () => {
    setUserId(await loadState('user').id)
    setPeerId(await loadState('peer'))
    setRole(await loadState('role').toString())
    setMyQuestion(await loadState('myQuestion'))
    setPeerQuestion(await loadState('peerQuestion'))
  }

  useEffect(()=> {
    if (role !== "" && myQuestion !== "" && peerQuestion !== "" 
        && userId !== "" && peerId !== "") {
      if (editingUrl[editingUrl.length - 1] === "/") {
        setEditingUrl(editingUrl.concat(userId))
      }
      setReadyToStart(true)
    } 
  },[role, myQuestion, peerQuestion, userId, peerId])

  const quiteInterview = async () => {
    console.log("quiteInterview")
    setShouldConnectWss(false)
    if (await userLeaveInterview(userId, myQuestion.questionId, myInputAsInterviewee)) {
      setModalTitle("Quit Successfully")
      setModalMsg("We'll save your approaches, please head to history to check the details")
      setIsOpenForLeavingInterview(true)
    } else {
      setModalTitle("Error!")
      setModalMsg("response.message")
      setIsOpen(true)
    }
  }


  const {
    sendJsonMessage,
    lastMessage,
  } = useWebSocket(editingUrl, {
    onOpen: () => console.log('opened'),
    retryOnError: true,
    onError: (err)=> console.log(err),
    reconnectInterval: 3000,
    reconnectAttempts: 10,
    shouldReconnect: () => true,
  }, shouldConnectWss);

  useEffect(() => {
    if (lastMessage !== undefined && lastMessage !== null) {
      console.log("lastMessage", lastMessage)
      if (isJson(lastMessage.data)) {
        var msg = JSON.parse(lastMessage.data)
        console.log(msg)
        if (msg.switchRole !== undefined && msg.switchRole) {
          changeRoleByPeerRequest();
        } else {
          setPeerInput(prev => msg.data);
        }
      }
    }
  }, [lastMessage]);

  // unsub wss when user close the page
  window.onunload = () => {
    setShouldConnectWss(false)
  }

  const webSocketMsg = () => {
    var msg = role === "0" ? myInputAsInterviewee : myInputAsInterviewer
    console.log(msg, role)
      return {
        "peerId": peerId,
        "data": msg, 
      }
  }

  const switchRoleRequest = () => {
    console.log("{\"peerId\": " +peerId+", \"switchRole\": true}")
    return {
      "peerId": peerId,
      "switchRole": true
    }
  }

  const handleUserInputAsInterviewee = (event) => {
    setmyInputAsInterviewee(event.target.value)
    sendJsonMessage(webSocketMsg())
  }

  const handleUserInputAsInterviewer = (event) => {
    setmyInputAsInterviewer(event.target.value)
    sendJsonMessage(webSocketMsg())
  }
  
  const changeRole = async () => {
    if (await userChangeRole(role)) {
      setReadyToStart(false)
      loadFromState()
    }
  }

  const requestRoleChange = async () => {
    console.log("requestRoleChange")
    setModalTitle("Role Switched")
    setModalMsg(`Now you are ${parseRole(role)}`)
    setReadyToStart(false)
    await changeRole()
    setCount(count + 1)
    sendJsonMessage(switchRoleRequest())
    setIsOpenForSwitchingRole(true)
  }

  const changeRoleByPeerRequest = async () => {
    console.log("changeRoleByPeerRequest")
    setModalTitle("Role Switched")
    setModalMsg(`Now you are ${parseRole(role)}`)
    setReadyToStart(false)
    await changeRole()
    setCount(count + 1)
    setIsOpenForSwitchingRole(true)
  }

  return (
    <div className="relative flex flex-col mx-auto min-w-0 break-words mb-6 shadow-lg rounded-lg bg-blueGray-100 border-0 h-screen93 w-11/12"
        style={{ minWidth: "900px"}}>
          <Modal title={modalTitle} msg={modalMsg} isOpen={isOpen} setIsOpen={setIsOpen}></Modal>
          <Modal title={modalTitle} msg={modalMsg} isOpen={isOpenForLeavingInterview} setIsOpen={setIsOpenForLeavingInterview} callBack={() => {history.push('/my/history')}}></Modal>
          <Modal title={modalTitle} msg={modalMsg} isOpen={isOpenForSwitchingRole} setIsOpen={setIsOpenForSwitchingRole} callBack={() => history.push('/interview')}></Modal>
          <InterviewSidebar role={role} quiteInterview={quiteInterview} requestRoleChange={requestRoleChange} callBack={() => history.push('/my/dashboard')} count={count}></InterviewSidebar>
      <div className="h-full w-full justify-start flex">
        <div className="w-full px-4 pt-6 flex flex-row justify-between">
          <div className="w-full max-w-md p-2 mx-auto bg-white rounded-2xl mb-6 overflow-y-auto">
            {!readyToStart ? null : role === "1"
              ? <CustomDisclosure title="Question Description" body={peerQuestion !== null ? peerQuestion.question : "Loading..."}></CustomDisclosure>
              : <CustomDisclosure title="Question Description" body={myQuestion !== null ? myQuestion.question : "Loading..."}></CustomDisclosure> }       
          </div>

          <div className="flex-1 px-4 mt-3">
            <div className="flex flex-col min-w-280-px">
              <Box
                  component="form"
                  noValidate
                  autoComplete="off">
                  <TextField
                   style={{paddingTop:"10px", height:"80%", width:"100%"}}
                   id="msg-from-socket"
                    label={role === "0" ? "The interviewer's suggestion" : "The interviewee's attempts"}
                    disabled
                    multiline
                    variant="outlined"
                    color="primary"
                    rows={17}
                    value={peerInput}
                  />
              </Box>
              <Box
                  hidden={role === "1"}
                  component="form"
                  noValidate
                  className="focus:outline-none"
                  autoComplete="off">
                  <TextField
                    className="focus:outline-none"
                    id="filled-multiline-flexible"
                    label="My approaches"
                    style={{height:"80%", width:"100%", marginTop: "30px"}}
                    multiline
                    variant="outlined"
                    color="primary"
                    rows={16}
                    value={myInputAsInterviewee}
                    onChange={handleUserInputAsInterviewee}
                  />
              </Box>
              <Box
                  hidden={role === "0"}
                  component="form"
                  noValidate
                  className="focus:outline-none"
                  autoComplete="off">
                  <TextField
                    className="focus:outline-none"
                    id="filled-multiline-flexible"
                    label="My suggestion"
                    style={{height:"80%", width:"100%", marginTop: "30px"}}
                    multiline
                    variant="outlined"
                    color="primary"
                    rows={16}
                    value={myInputAsInterviewer}
                    onChange={handleUserInputAsInterviewer}
                  />
              </Box>
            </div>
          </div>
          
          {!readyToStart ? null : role === "1" 
            ? <div className="w-full max-w-md p-2 mx-auto bg-white rounded-2xl h-auto mb-6 overflow-y-auto">
                <CustomDisclosure title="Result" body={peerQuestion !== null ? peerQuestion.solution : "Loading..."}></CustomDisclosure>
              </div> 
            : null}
        </div>
      </div>
    </div>
  )
}
