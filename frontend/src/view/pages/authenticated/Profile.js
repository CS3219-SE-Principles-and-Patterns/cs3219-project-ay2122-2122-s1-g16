import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import { Modal } from "../../component/Modal";
import { userPreRestPassword, userUpdateUsername } from "../../../redux/userSlice";
import { loadState } from "../../../redux/localStateManagement";
import { IconButton } from "@mui/material";
import DoneOutlinedIcon from '@mui/icons-material/DoneOutlined';
import ModeEditOutlinedIcon from '@mui/icons-material/ModeEditOutlined';
import { useDispatch } from "react-redux";

const UsernameInput = ({isLoading, isUpdatingUsername, username, setUsername}) => {
  return (
    <div className="relative w-6/12 mb-3">
      <label
        className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
        htmlFor="grid-password"
      >
        Username
      </label>
      <input
        disabled={!isUpdatingUsername || isLoading}
        type="text"
        value={username}
        className={ 
            "border-0 px-3 py-3 " +
            "placeholder-blueGray-300 bg-white " +
            "rounded text-sm shadow " + 
            "focus:outline-none focus:ring w-full ease-linear transition-all duration-150 " + 
            (isUpdatingUsername ? "text-blueGray-600" : "text-blueGray-400")}
        placeholder="Username"
        onChange={(event) => {
          setUsername(event.target.value)
        }}
      />
    </div>
  )
}

export default function Profile() {
  let user = loadState('user');
  const email = user !== null ? user.email : ""
  const [isOpen, setIsOpen] = useState(false)
  const [isLoading, setIsLoading] = useState(false)
  const [enableSubmit, setEnableSubmit] = useState(false)
  const [isUpdatingUsername, setIsUpdatingUsername] = useState(false)
  const [modalTitle, setModalTitle] = useState("")
  const [modalMsg, setModalMsg] = useState("")
  const history = useHistory()
  const [username, setUsername] = useState(user !== null ? user.username : "")

  const dispath = useDispatch()

    useEffect(()=>{
      if ( user == null) {
        history.push("/login")
        return 
      }
      
      if (isUpdatingUsername && username === "") {
        setEnableSubmit(false)
      } else {
        setEnableSubmit(true)
      }
    },[isUpdatingUsername,username])

    const handleResetPassword = async () => {
      setIsLoading(true)
      userUpdateUsername(email)
      var response = await userPreRestPassword(email)
      if (response) {
        setModalTitle("Reset password")
        setModalMsg("An email will be sent to your register email account, please follow the instruction there to reset your password." )
      }
      setIsOpen(true)
      setIsLoading(false)
      }
    

    const handleUpdateUsername = () => {
      if (!isUpdatingUsername) {
        setIsUpdatingUsername(!isUpdatingUsername)
        return 
      }
      setIsLoading(true)
      dispath(userUpdateUsername(user.id, username)).then((response)=>{
        if(response) {
          setModalTitle("Username Updated")
          setModalMsg("Your username has been updated successfully." )
          user = loadState('user')
          setEnableSubmit(false)
          setIsUpdatingUsername(false)
        } 
        setIsOpen(true)
        setIsLoading(false)
      })
    }
  

  return (
    <>
      {isOpen ? 
        <Modal title={modalTitle} msg={modalMsg}
            isOpen={isOpen} setIsOpen={setIsOpen}></Modal>
      : null}
      <div className="relative flex flex-col mx-auto min-w-0 break-words mb-6 shadow-lg rounded-lg bg-blueGray-100 border-0 bg-no-repeat h-screen80 w-full"
        style={{ 
            minHeight: "780px",
            minWidth: "900px",
            backgroundPosition:"right bottom",
            backgroundImage: "url(" + require("../../../assets/img/bg_1.png").default + ")"}}>
        <div className="rounded-t bg-white mb-0 px-6 py-6">
          <div className="text-center flex justify-between">
            <h6 className="text-blueGray-700 text-xl font-bold">My account</h6>
          </div>
        </div>
        <div className="flex-auto px-4 lg:px-10 pt-6">
          <form>
            <h6 className="text-blueGray-400 text-md mt-6 mb-6 font-bold uppercase">
              User Information
            </h6>
            <div className="flex flex-col">
              <div className="flex flex-row w-full px-4 mt-6">
                <div className="relative w-6/12 mb-3">
                  <label
                    className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                    htmlFor="grid-password"
                  >
                    Email address
                  </label>
                  <input
                    disabled
                    type="text"
                    className="border-0 px-3 py-3 
                    placeholder-blueGray-300 text-blueGray-400 bg-white 
                    rounded text-sm shadow 
                    focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                    defaultValue={email}
                    placeholder="Email"
                  />
                </div>
              </div>
              <div className="flex flex-row w-full px-4 mt-16">
                <UsernameInput isLoading={isLoading} isUpdatingUsername={isUpdatingUsername}
                              username={username} setUsername={setUsername}></UsernameInput>
                <IconButton 
                    className="focus:outline-none"
                    sx={{marginLeft: "20px", marginTop: "10px", }}
                    type="button"
                    disabled={isUpdatingUsername && !enableSubmit}
                    onClick={handleUpdateUsername}>
                    {isUpdatingUsername ? (
                      <DoneOutlinedIcon fontSize="large" sx={{marginX:"8px"}}/>
                    ) : (
                      <ModeEditOutlinedIcon fontSize="large"sx={{marginX:"8px"}}/>
                    )}
                </IconButton>
              </div>
                
              <div className="flex flex-row w-full px-4 mt-20">
                <button className="relative w-6/12 mb-3 bg-transparent border border-solid font-bold uppercase text-xs px-4 py-3 rounded outline-none 
                border-blueGray-500 text-blueGray-500
                hover:bg-blueGray-500 hover:text-white active:bg-blueGray-600 
                focus:outline-none ease-linear transition-all duration-150" type="button"
                onClick={handleResetPassword}>
                  <i className="fas fa-key"></i> Update Password
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}
