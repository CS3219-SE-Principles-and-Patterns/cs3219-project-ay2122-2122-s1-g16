/*eslint-disable*/
import React, { useEffect, useState } from "react";
import { useDispatch } from 'react-redux';
import { useHistory } from "react-router-dom";
import { userLogout } from "../../../redux/userSlice";
import { Link } from "react-router-dom";
import { Button, IconButton } from '@mui/material';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';
import HistoryOutlinedIcon from '@mui/icons-material/HistoryOutlined';
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import LaptopChromebookOutlinedIcon from '@mui/icons-material/LaptopChromebookOutlined';
import CloseOutlinedIcon from '@mui/icons-material/CloseOutlined';
import MenuOpenOutlinedIcon from '@mui/icons-material/MenuOpenOutlined';

export default function Sidebar() {
  const [collapseShow, setCollapseShow] = useState("hidden");
  const history = useHistory();
  const dispatch = useDispatch();
  
  const handleLogout = () => {
    dispatch(userLogout());
    history.push('/');  
  }


  const NavButtonList = () => {
    return (
      <ul className="md:flex-col md:min-w-full flex flex-col list-none">
        <li className="items-center mb-2 mt-4">
          <Link
            className={
              "text-xs uppercase py-3 font-bold block " +
              (window.location.href.indexOf("/my/dashboard") !== -1
                ? "text-blueGray-300 hover:text-blueGray-300"
                : "text-blueGray-500 hover:text-blueGray-500")
            }
            to="/my/dashboard"
          >
            <Button 
              className="mr-2 text-xl align-middle opacity-75 focus:outline-none"
              sx={{paddingLeft:"10px", paddingRight: "75px", fontWeight: "bold", marginLeft:"10px"}}
              color="inherit"
              disabled={window.location.href.indexOf("/my/dashboard") !== -1}
              startIcon={<LaptopChromebookOutlinedIcon/>}
              >
              Dashboard
            </Button>{" "}
          </Link>
        </li>

        <li className="items-center mb-2">
          <Link
            className={
              "text-xs uppercase py-3 font-bold block " +
              (window.location.href.indexOf("/my/profile") !== -1
                ? "text-blueGray-300 hover:text-blueGray-300"
                : "text-blueGray-500 hover:text-blueGray-500")
            }
            to="/my/profile"
          >
            <Button 
              className="mr-2 text-xl align-middle opacity-75 focus:outline-none"
              sx={{paddingLeft:"10px", paddingRight: "75px", fontWeight: "bold", marginLeft:"10px"}}
              color="inherit"
              disabled={window.location.href.indexOf("/my/profile") !== -1}
              startIcon={<AccountCircleOutlinedIcon/>}
              >
              Profile
            </Button>{" "}
          </Link>
        </li>

        <li className="items-center">
          <Link
            className={
              "text-xs uppercase py-3 block " +
              (window.location.href.indexOf("/my/history") !== -1
                ? "text-blueGray-300 hover:text-blueGray-300"
                : "text-blueGray-500 hover:text-blueGray-500")
            }
            to="/my/history"
          >
            <Button 
              className="mr-2 text-xl align-middle opacity-75 focus:outline-none"
              sx={{paddingLeft:"10px", paddingRight: "75px", fontWeight: "bold", marginLeft:"10px"}}
              color="inherit"
              disabled={window.location.href.indexOf("/my/history") !== -1}
              startIcon={<HistoryOutlinedIcon/>}
              >
              History
            </Button>{" "}
          </Link>
        </li>
      </ul>
    )
  }

  const CollapseHeader = () => {
    return (
      <div className="md:min-w-full md:hidden block pb-4 mb-4">
        <div className="flex flex-wrap">
          <div className="w-6/12">
            <div className="md:block text-xleft md:pb-2 text-blueGray-600 
                mr-0 inline-block whitespace-nowrap text-xlx uppercase font-bold p-4 px-0">
              Peerprep
            </div>
          </div>
          <div className="w-6/12 flex justify-end">
          <IconButton
            className="focus:outline-none"
            color="inherit"
            onClick={() => setCollapseShow("hidden")} 
            size="large">
            <CloseOutlinedIcon 
                fontSize="inherit"
            />
          </IconButton>
          </div>
        </div>
      </div>
    )
  }

  return (
    <>
      <nav className="md:left-0 md:block md:fixed md:top-0 md:bottom-0 md:overflow-y-auto md:flex-row md:flex-nowrap md:overflow-hidden shadow-xl 
      bg-white flex flex-wrap items-center justify-between relative md:w-48 z-10 py-4 px-0 ">
        <div className="md:flex-col md:items-stretch md:min-h-full md:flex-nowrap px-0 flex flex-wrap items-center justify-between w-full mx-auto">
          {/* Toggler */}

          <div className="md:hidden">
            <IconButton
              className="focus:outline-none"
              sx={{marginLeft:"16px"}}
              color="inherit"
              onClick={() => setCollapseShow("bg-white m-2 py-3 px-6")} 
              size="large">
              <MenuOpenOutlinedIcon 
                fontSize="inherit"
              />
            </IconButton>
          </div>
          <div className="md:block text-xleft md:pb-2 text-blueGray-600 mr-0 inline-block whitespace-nowrap text-xl uppercase font-bold p-4 px-6">
            Peerprep
          </div>

          <div
            className={ "md:flex md:flex-col md:items-stretch md:opacity-100 md:relative md:mt-4 md:shadow-none " +
              "shadow absolute top-0 left-0 right-0 z-40 overflow-y-auto overflow-x-hidden h-auto items-center flex-1 rounded " + collapseShow
            }>
            <CollapseHeader></CollapseHeader>
            <hr className="my-2 md:min-w-full" />          
            <NavButtonList></NavButtonList>
          </div>
          <div>
            <div className="text-xs uppercase py-3 font-bold w-full justify-items-center
            text-blueGray-700 hover:text-blueGray-500">
              <div className="md:block sm:hidden">
                <Button 
                  className="mr-2 text-xl align-middle opacity-75 focus:outline-none py-4"
                  sx={{paddingLeft:"10px", paddingRight: "75px", fontWeight: "bold", marginLeft:"10px"}}
                  onClick={handleLogout}
                  color="inherit"
                  startIcon={<LogoutOutlinedIcon/>}
                  >
                  Log out
                </Button>
              </div>
              <div className="md:hidden">
                <IconButton
                  className="focus:outline-none"
                  sx={{marginRight:"16px"}}
                  color="inherit"
                  onClick={handleLogout}
                  size="large">
                  <LogoutOutlinedIcon/>
                </IconButton>
              </div>
            
            </div>
          </div>
        </div>
      </nav>
    </>
  );
}
