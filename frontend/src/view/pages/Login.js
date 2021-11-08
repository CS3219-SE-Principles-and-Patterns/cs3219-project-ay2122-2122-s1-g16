import React, { useState, useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import CircularProgress from '@mui/material/CircularProgress';
import Navbar from "../component/sidebar/UnauthSidebar";
import Footer from "../component/Footer";
import { userLogin, userPreRestPassword } from '../../redux/userSlice';
import { isValidEmail } from "../../util/util";
import { Modal } from '../component/Modal';
import { useDispatch } from 'react-redux';

const EmailInput = ({setEmail, email, isLoading}) => {
    
  return (
      <div className="relative w-full mb-5">
      <label
          className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
          htmlFor="grid-password">
          Email
      </label>
      <input
          key="email"
          type="email"
          disabled={isLoading}
          className="border-0 px-3 py-3 
          placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow 
          focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
          placeholder="Email"
          value={email}
          onChange={event => setEmail(event.target.value)}/>
      </div>
  )
}

const PasswordInput = ({setPassword, password, isLoading}) => {

  return (
      <div className="relative w-full mb-5">
          <label
              className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
              htmlFor="grid-password">
              Password
          </label>
          <input
              key="password"
              type="password"
              disabled={isLoading}
              className="border-0 px-3 py-3 
              placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow 
              focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
              placeholder="Password"
              value={password}
              onChange={event => setPassword(event.target.value)}
          />
      </div>
  )   
}

const ErrorMsg = ({errorMsg=""}) => {
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
      <ErrorMsgSpan msg={errorMsg}></ErrorMsgSpan>
    </>
  )
}

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [showErrorMsg, setShowErrorMsg] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [enableSubmit, setEnableSubmit] = useState(false);
  const [isOpen, setIsOpen] = useState(false)
  const [modalTitle, setModalTitle] = useState("")
  const [modalMsg, setModalMsg] = useState("")
  const history = useHistory();
  const dispatch = useDispatch()

  useEffect(() => {
    var msg = ""

    if (email !== "" && !isValidEmail(email)) {
      msg = "Invalid email format, please check again."
    } else {
      msg = ""
    }

    if (showErrorMsg && errorMsg !== "" && email === "") {
      msg = errorMsg;
    }

    if (email !== ""  && password !== "" && msg === "") {
      setEnableSubmit(true)
    } else {
      setEnableSubmit(false)
    }

    setErrorMsg(msg)
  },[email, password])

  const onSubmit = async () => {
    setIsLoading(true);
    dispatch(userLogin(email.trim(), password)).then((isSuccess) => {
      setIsLoading(false);
      if (isSuccess) {
        history.push("/my/dashboard");
      }
    });
  };

  const ForgetPasswordLink = () => {

    const hanleForgetPassword = async () => {
      if (email === "") {
        setShowErrorMsg(true)
        setErrorMsg("Please provide your email before password resetting.")
      } else {
        setShowErrorMsg(false)
        if(isValidEmail(email)) {
          var response = await userPreRestPassword(email.trim())
          if (response) {
            setModalTitle("Forgot password")
            setModalMsg("An email will be sent to your register email account, please follow the instruction there to reset your password." )
          } 
          setIsOpen(true)
        }
      }
    }

    return (
      <div className="flex flex-wrap mt-6 relative">
        <div className="w-full text-right">
          <a
            onClick={hanleForgetPassword}
            className="text-blueGray-200 py-2 px-2 rounded-lg
            hover:bg-blueGray-500 hover:backdrop-opacity-25 hover:shadow-md hover:outline-none focus:bg-blueGray-300 mr-1 mb-1 
            ease-linear transition-all duration-50"
            disabled={isLoading}
          >
            <small>Forgot password?</small>
          </a>
        </div>
      </div>
    )
  }


  const WelcomeTitle = () => {
    return (
      <div className="rounded-t mb-0 px-6 py-6">
        <div className="text-center mb-3 pb-6 pt-6">
          <h2 className="text-blueGray-700 text-3xl text-center mb-3 font-bold">
            Welcome back to Peerprep
          </h2>
        </div>
        <hr className="mt-6 border-b-1 border-blueGray-300" />
      </div>
    )
  }

  const LoginButton = () => {
    return (
      <Box sx={{ m: 1, position: 'relative' }}>
        <Button
          className="text-sm font-bold uppercase px-6 py-3
          rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1 w-full ease-linear transition-all duration-150"
          type="button"
          disabled={!enableSubmit||isLoading}
          sx={{marginTop: "20px"}}
          variant="contained"
          onClick={onSubmit}>
          Sign In
        </Button>
        {isLoading && (
          <CircularProgress
            size={24}
            sx={{
              color: "#00cc00",
              position: 'absolute',
              top: '70%',
              left: '50%',
              marginTop: '-12px',
              marginLeft: '-12px',
            }}
          />)}
        </Box>
    )
  }
  
  return (
    <>
    <Modal title={modalTitle} msg={modalMsg}
            isOpen={isOpen} setIsOpen={setIsOpen}></Modal>
    <Navbar showLoginBtn={false} showRigBtn={true} />
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
            <WelcomeTitle></WelcomeTitle>
            <div className="flex-auto px-4 lg:px-10 py-10 pt-0">
              <EmailInput setEmail={setEmail} email={email} isLoading={isLoading}></EmailInput>
              <PasswordInput setPassword={setPassword} password={password} isLoading={isLoading}></PasswordInput>
              <ErrorMsg errorMsg={errorMsg}></ErrorMsg>
              <hr className={`${errorMsg !== "" ? "border-b-1 border-blueGray-300 mt-6 " : "border-b-1 border-blueGray-300 mt-8"}`} />
              <div className="text-center mt-12">
              <LoginButton></LoginButton>
              </div>
            </div>
          </div>
          <ForgetPasswordLink></ForgetPasswordLink>
        </div>
      </div>
      <Footer position="absolute w-full bottom-0" bg="" />
    </section>
    </>
  );
}
