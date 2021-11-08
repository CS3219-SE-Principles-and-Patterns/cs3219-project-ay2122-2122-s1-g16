/*eslint-disable*/
import React, { useState } from "react";
import { IconButton } from "@mui/material";
import CachedOutlinedIcon from '@mui/icons-material/CachedOutlined';
import ExitToAppOutlinedIcon from '@mui/icons-material/ExitToAppOutlined';


export default function InterviewSidebar({role, quiteInterview, requestRoleChange, count}) {
  const NavigationBtnSet = () => {
    return (
      <ul className="flex-col min-w-full flex list-none bottom-0">
        <li className="items-center">
          <IconButton
            className="focus:outline-none"
            disabled={role === "0" || count >= 1}
            color="inherit"
            size="large"
            onClick={requestRoleChange}>
            <CachedOutlinedIcon/> 
          </IconButton>
        </li>

        <li className="items-center">
          <IconButton
            className="focus:outline-none"
            disabled={count < 1}
            color="inherit"
            size="large"
            onClick={quiteInterview}>
            <ExitToAppOutlinedIcon/>
          </IconButton>
        </li>
      </ul>
    )
  }
  return (
    <>
      <nav className="left-0 block fixed top-0 bottom-0 z-10 py-4 px-6 group-hover 
                    overflow-y-auto flex-row flex-nowrap overflow-hidden 
                    shadow-xl bg-white items-center justify-between ">
        <div className="min-h-full flex-nowrap
                    flex flex-col justify-end opacity-100 relative mt-4 shadow-none h-full rounded">
        
            <hr className="my-2 min-w-full" />
            <NavigationBtnSet></NavigationBtnSet>
            <hr className="my-2 min-w-full" />
        </div>
      </nav>
    </>
  );
}
