import React, {useState, useEffect} from "react";
import { useHistory } from "react-router-dom";
import Navbar from "../component/sidebar/UnauthSidebar";
import Footer from "../component/Footer";
import Button from '@mui/material/Button';
import { Box } from "@mui/material";
import CircularProgress from '@mui/material/CircularProgress';
import { Modal } from "../component/Modal";
import { loadState } from "../../redux/localStateManagement";
import { isValidEmail } from "../../util/util";
import { userRegister, userRestPassword } from "../../redux/userSlice";


const UsernameInput = ({setUsername, username, inputClassName, isPasswordRestPage, isLoading}) => {
  if (isPasswordRestPage) {
    return null
  }
  return (
    <div className="relative w-full mb-5">
      <label
        className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
        htmlFor="grid-password">
        Username
      </label>
      <input
        type="email"
        disabled={isLoading}
        className={inputClassName}
        value={username}
        onChange={event => setUsername(event.target.value)}
        placeholder="Userame"
      />
    </div>
  )
}

const EmailInput = ({setEmail, email, inputClassName, isLoading}) => {
  return (
    <div className="relative w-full mb-5">
      <label
        className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
        htmlFor="grid-password">
        Email
      </label>
      <input
        type="email"
        disabled={isLoading}
        className={inputClassName}
        value={email}
        onChange={(event) => setEmail(event.target.value)}
        placeholder="Email"
      />
    </div>
  )
}

const PasswordInput = ({setPassword, password, inputClassName, isLoading}) => {
  return (
    <div className="relative w-full mb-5">
      <label
        className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
        htmlFor="grid-password">
        Password
      </label>
      <input
        type="password"
        disabled={isLoading}
        className={inputClassName}
        value={password}
        onChange={event => setPassword(event.target.value)}
        placeholder="Password"
      />
    </div>
  )
}

const ConfirmPasswordInput = ({setConfirmPassword, confirmPassword, inputClassName, isLoading}) => {
  return (
    <div className="relative w-full mb-2">
      <label
        className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
        htmlFor="grid-password">
        Confirm Password
      </label>
      <input
        type="password"
        disabled={isLoading}
        className={inputClassName}
        value={confirmPassword}
        onChange={event => setConfirmPassword(event.target.value)}
        placeholder="Confirm Password"
      />
    </div>
  )
}

const ErrorMsg = ({emailErrorMsg="", confirmPasswordErrorMsg=""}) => {
  const ErrorMsgSpan= ({msg=""}) => {
    if (msg !== "") {
      return (
        <span className="flex items-center font-medium tracking-wide text-red-500 text-xs ml-1">
          {msg}
        </span> )
    }
    return null
}
  return(
    <>
      <ErrorMsgSpan msg={emailErrorMsg}></ErrorMsgSpan>
      <ErrorMsgSpan msg={confirmPasswordErrorMsg}></ErrorMsgSpan>
    </>
  )
}

export default function Register() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const inputClassName = "border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150";
  const [emailErrorMsg, setEmailErrorMsg] = useState("");
  const [confirmPasswordErrorMsg, setConfirmPasswordErrorMsg] = useState("");
  const [enableSubmit, setEnableSubmit] = useState(false);
  const isPasswordRestPage = window.location.href.indexOf("/register") === -1;
  const [isOpen, setIsOpen] = useState(false)
  const [isLoading, setIsLoading] = useState(false);
  const [modalTitle, setModalTitle] = useState("")
  const [modalMsg, setModalMsg] = useState("")
  const [isRegisterTernimated, setIsRegisterTernimated] = useState(false)
  const history = useHistory()

  const UnauthNav = () => {
    if(isPasswordRestPage) {
      return null
    } else {
      return (<Navbar showLoginBtn={true} showRigBtn={false}  />)
    }
  }

  useEffect(() => {
    if (isPasswordRestPage && !isOpen && (loadState("token") === null || 
        window.location.href.indexOf(loadState("token")) === -1)) {
      history.push("/login");
      return;
    }

    if (isRegisterTernimated && !isOpen) {
      history.push("/login");
      return;
    }

    var emailError = ""
    var confirmPasswordError = ""

    if (email !== "" && !isValidEmail(email)) {
      emailError = "Invalid email format, please check again."
    } else {
      emailError = ""
    }

    if ((password !== "" && confirmPassword !== "") && (password !== confirmPassword)) {
      confirmPasswordError = "The password and confirm password are different, please check again."
    } else {
      confirmPasswordError = ""
    }

    if ((username !== "" || isPasswordRestPage) 
          && email !== ""  && password !== "" && confirmPassword !== "" 
          && emailError === "" && confirmPasswordError === "") {
      setEnableSubmit(true)
    } else {
      setEnableSubmit(false)
    }

    setEmailErrorMsg(emailError)
    setConfirmPasswordErrorMsg(confirmPasswordError) 
  }, [email, username, password, confirmPassword])

  const handleSubmit = () => {
    setIsLoading(true)
    if (isPasswordRestPage) {
      if (userRestPassword(email, password, loadState('token'))) {
        setModalTitle("Password updated")
        setModalMsg("Your password has been updated successfully, please login with your new password.")       
      } 
    } else {
      if (userRegister(email, username, password)) {
        setModalTitle("Pending active")
        setModalMsg("An email will be sent to your register email account, please follow the instruction there to active your account.")
      } 
      setIsRegisterTernimated(true)    
    }
    setIsLoading(false)
    setIsOpen(true)
  }

  const RegisterButton = () => {
    return (
      <div className="text-center mt-12">
        <Box sx={{ position: 'relative' }}>
          
          <Button
            className="text-white text-sm font-bold uppercase px-6 py-3 rounded shadow
            hover:shadow-lg outline-none focus:outline-none mr-1 mb-1 w-full ease-linear transition-all duration-150"
            disabled={!enableSubmit || isLoading}
            onClick={handleSubmit}
            variant="contained"
            type="button">
              {isPasswordRestPage ? "Confirm" : "Create Account"}
          </Button>
          {isLoading && (
            <CircularProgress
              size={24}
              sx={{
                color: "#00cc00",
                position: 'absolute',
                top: '50%',
                left: '50%',
                marginTop: '-12px',
                marginLeft: '-12px',
              }}
            />)}
          </Box>
        </div>
    )
  }

  return (
    <>
    <Modal title={modalTitle} msg={modalMsg} isOpen={isOpen} setIsOpen={setIsOpen}></Modal>
    <UnauthNav></UnauthNav>
      <section className="relative w-full h-full py-40 min-h-screen">
        <div
          className="absolute top-0 w-full h-full bg-blueGray-800 bg-no-repeat bg-full"
          style={{
            backgroundImage:
              "url(" + require("../../assets/img/auth_bg.png").default + ")",
          }}
        ></div>
          <div className="container mx-auto px-4 h-full flex content-center items-center justify-center">
            <div className="w-full lg:w-6/12 px-4">
              <div className="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded-lg bg-blueGray-200 border-0">
                <div className="rounded-t mb-0 px-6 py-6">
                  <div className="text-center mb-3 pb-6 pt-6">
                  <h2 className="text-blueGray-700 text-3xl text-center mb-3 font-bold">
                      {isPasswordRestPage ? "Password Reset" : "Welcome to Peerprep"}
                    </h2>
                  </div>
                  <hr className="mt-6 border-b-1 border-blueGray-300" />
                </div>
                <div className="flex-auto px-4 lg:px-10 py-10 pt-0">
                  <UsernameInput setUsername={setUsername} username={username} inputClassName={inputClassName} 
                  isPasswordRestPage={isPasswordRestPage}></UsernameInput>
                  <EmailInput setEmail={setEmail} email={email} inputClassName={inputClassName} isLoading={isLoading}></EmailInput>
                  <PasswordInput setPassword={setPassword} password={password} inputClassName={inputClassName} isLoading={isLoading}></PasswordInput>
                  <ConfirmPasswordInput setConfirmPassword={setConfirmPassword} confirmPassword={confirmPassword} 
                                        inputClassName={inputClassName} isLoading={isLoading}></ConfirmPasswordInput>
                  <ErrorMsg emailErrorMsg={emailErrorMsg} confirmPasswordErrorMsg={confirmPasswordErrorMsg} isLoading={isLoading}></ErrorMsg>
                  <hr className={`${(emailErrorMsg !== "" || confirmPasswordErrorMsg !== "") ? "border-b-1 border-blueGray-300 mt-6" : "border-b-1 border-blueGray-300 mt-8"}`} />                 
                  <RegisterButton></RegisterButton>
                </div>
              </div>
          </div>
        </div>
        <Footer position="absolute w-full bottom-0" bg="" />
      </section>
    </>
  );
}
