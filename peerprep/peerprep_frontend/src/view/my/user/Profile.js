import React from "react";
import { Dialog, Transition } from '@headlessui/react'
import { Fragment, useState } from 'react'

// components

export default function Profile() {
    const [editable, setEditable] = React.useState(false);
    let [isOpen, setIsOpen] = React.useState(false);
    
    function toggleOpen() {
      setIsOpen(!isOpen)
    }

    function PasswordModal() {
      return (
        <>
          <div className="fixed inset-0 flex items-center justify-center">
            <button
              type="button"
              onClick={toggleOpen}
              className="px-4 py-2 text-sm font-medium text-white bg-black rounded-md bg-opacity-20 hover:bg-opacity-30 focus:outline-none focus-visible:ring-2 focus-visible:ring-white focus-visible:ring-opacity-75"
            >
              Open dialog
            </button>
          </div>
    
          <Transition appear show={isOpen} as={Fragment}>
            <Dialog
              as="div"
              className="fixed inset-0 z-10 overflow-y-auto"
              onClose={toggleOpen}
            >
              <div className="min-h-screen px-4 text-center">
                <Transition.Child
                  as={Fragment}
                  enter="ease-out duration-300"
                  enterFrom="opacity-0"
                  enterTo="opacity-100"
                  leave="ease-in duration-200"
                  leaveFrom="opacity-100"
                  leaveTo="opacity-0"
                >
                  <Dialog.Overlay className="fixed inset-0" />
                </Transition.Child>
    
                {/* This element is to trick the browser into centering the modal contents. */}
                <span
                  className="inline-block h-screen align-middle"
                  aria-hidden="true"
                >
                  &#8203;
                </span>
                <Transition.Child
                  as={Fragment}
                  enter="ease-out duration-300"
                  enterFrom="opacity-0 scale-95"
                  enterTo="opacity-100 scale-100"
                  leave="ease-in duration-200"
                  leaveFrom="opacity-100 scale-100"
                  leaveTo="opacity-0 scale-95"
                >
                  <div className="inline-block w-full max-w-md p-6 my-8 overflow-hidden text-left align-middle transition-all transform bg-white shadow-xl rounded-2xl">
                    <Dialog.Title
                      as="h3"
                      className="text-lg font-medium leading-6 text-gray-900"
                    >
                      Update password
                    </Dialog.Title>
                    <div className="mt-2">
                      <p className="text-sm text-gray-500">
                        An email will be sent to your register email account, please follow the instruction there to reset your password.
                      </p>
                    </div>
    
                    <div className="mt-4">
                      <button
                        type="button"
                        className="inline-flex justify-center px-4 py-2 text-sm font-medium text-blue-900 bg-blue-100 border border-transparent rounded-md hover:bg-blue-200 focus:outline-none focus-visible:ring-2 focus-visible:ring-offset-2 focus-visible:ring-blue-500"
                        onClick={toggleOpen}
                      >
                        Got it, thanks!
                      </button>
                    </div>
                  </div>
                </Transition.Child>
              </div>
            </Dialog>
          </Transition>
        </>
      )
    }

  return (
    <>
      {isOpen ? 
        <PasswordModal></PasswordModal>
      : null}
      <div className="relative flex flex-col mx-auto min-w-0 break-words mb-6 shadow-lg rounded-lg bg-blueGray-100 border-0 bg-no-repeat h-screen80 w-full"
        style={{ 
            minWidth: "900px",
            backgroundPosition:"right bottom",
            backgroundImage: "url(" + require("../../../assets/img//bg_1.png").default + ")"}}>
        <div className="rounded-t bg-white mb-0 px-6 py-6">
          <div className="text-center flex justify-between">
            <h6 className="text-blueGray-700 text-xl font-bold">My account</h6>
          </div>
        </div>
        <div className="flex-auto px-4 lg:px-10 pt-6">
          <form>
            <h6 className="text-blueGray-400 text-md mt-3 mb-6 font-bold uppercase">
              User Information
            </h6>
            <div className="flex flex-col">
              <div className="flex flex-row w-full px-4 mt-2">
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
                    defaultValue="email placeholder uneditable"
                    placeholder="Email"
                  />
                </div>
              </div>
              <div className="flex flex-row w-full px-4 mt-6">
                <div className="relative w-6/12 mb-3">
                  <label
                    className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                    htmlFor="grid-password"
                  >
                    Username
                  </label>
                  <input
                    disabled={!editable}
                    type="text"
                    className={ 
                        "border-0 px-3 py-3 " +
                        "placeholder-blueGray-300 bg-white " +
                        "rounded text-sm shadow " + 
                        "focus:outline-none focus:ring w-full ease-linear transition-all duration-150 " + 
                        (editable ? "text-blueGray-600" : "text-blueGray-400")}
                    defaultValue="username placeholder"
                    placeholder="Username"
                  />
                </div>
                <button className="mt-2 mx-6 pt-1 bg-transparent outline-none 
                    focus:outline-none ease-linear transition-all duration-150" 
                    type="button"
                    onClick={() => {setEditable(!editable)}}>
                    <i className={(editable ? "far fa-check " : "far fa-edit ") +
                      "text-2xl font-bold align-baseline text-blueGray-600 " +
                      "hover:text-blueGray-300 active:bg-blueGray-300 focus:outline-none ease-linear transition-all duration-150"}></i>
                </button>
              </div>
                
              <div className="flex flex-row w-full px-4 mt-8">
                <button className="relative w-6/12 mb-3 bg-transparent border border-solid font-bold uppercase text-xs px-4 py-3 rounded outline-none 
                border-blueGray-500 text-blueGray-500
                hover:bg-blueGray-500 hover:text-white active:bg-blueGray-600 
                focus:outline-none ease-linear transition-all duration-150" type="button"
                onClick={()=>{setIsOpen(true)}}>
                  <i className="fas fa-key"></i> Update Password
                </button>
              </div>

              <hr className="mt-6 border-b-1 border-blueGray-300" />

            <h6 className="w-full text-blueGray-400 text-md mt-8 mb-6 font-bold uppercase">
              Account Summary
            </h6>
            <div className="flex flex-wrap">
              <div className="w-full lg:w-12/12 px-4">
                <div className="relative w-full mb-3">
                  <label
                    className="block text-blueGray-600 text-sm font-bold mb-4"
                    htmlFor="grid-password"
                  >
                    Number of interview took: 100
                  </label>

                  <label
                    className="block text-blueGray-600 text-sm font-bold mb-4"
                    htmlFor="grid-password"
                  >
                    Number of HARD question solved: 100
                  </label>

                  <label
                    className="block text-blueGray-600 text-sm font-bold mb-4"
                    htmlFor="grid-password"
                  >
                    Number of MEDIUM question solved: 100
                  </label>

                  <label
                    className="block text-blueGray-600 text-sm font-bold mb-2"
                    htmlFor="grid-password"
                  >
                    Number of EASY question solved: 100
                  </label>
                  
                </div>
              </div>
            </div>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}
