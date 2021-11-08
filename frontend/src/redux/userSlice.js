import { createSlice } from '@reduxjs/toolkit'
import { loadState, removeState, saveState } from './localStateManagement';
import { login, logout, preResetPassword, register, resetPassword, updateUserName } from '../apiHandler/accountRelatedApiHandler';
import { getHistoryRecords } from '../apiHandler/historyRelatedApiHandler';
import { sendCancelMatchRequest, sendMatchRequest } from '../apiHandler/matchRelatedApiHandler';
import { getInterviewQuestion, saveUserApproach } from '../apiHandler/interviewRelatedApiHandler';
import { parseHtml } from '../util/util';

const USER = 'user';
const TOKEN = 'token';
const HISTORY = 'history';
const PEER = 'peer';
const ROLE = 'role';
const MY_QUESTION = 'myQuestion';
const PEER_QUESTION = 'peerQuestion';

const persistedUser = loadState(USER);

const SUCCESS = true
const FAIL = false
const STATUS_SUCCESS = 200;
const STATUS_CREATED = 201;

export const userSlice = createSlice({
  name: 'user',
  initialState: persistedUser,
  reducers: {
    setUser: (state, action) => action.payload,
  },
});

export const { setUser } = userSlice.actions;

// ****************** Actions ******************

// Acount Related Actions 
export const userLogin = (email, password) => async (dispatch) => {
  var response = await login(email, password);
  if (response.status === STATUS_SUCCESS) {
    saveState(USER, response.data.data);
    await dispatch(setUser(response.data.data));
    return SUCCESS;
  } else {
    console.log(response);
    window.alert("Error: " + response.data.message);
    return FAIL;
  }
};

export const userLogout = () => async (dispatch) => {
  var response = await logout();
  if (response.status === STATUS_SUCCESS) {
    removeState(USER);
    removeState(HISTORY);
    await dispatch(setUser(null));
  } else {
    console.log(response);
    window.alert("Error: " + response.data.message);
    return FAIL;
  }
};

export const userRegister = async (email, username, password) => {
  var response = await register(email, username, password);
  if (response.status === STATUS_CREATED) {
    return SUCCESS;
  } else {
    console.log(response);
    window.alert("Error: " + response.data.message);
    return FAIL;
  }
};

export const userUpdateUsername = (id, newUsername) => async (dispatch) => {
  var response = await updateUserName(id, newUsername);
  if (response.status === STATUS_SUCCESS) {
    saveState(USER, response.data.data);
    await dispatch(setUser(response.data.data));
    return SUCCESS;
  } else {
    console.log(response);
    window.alert("Error: " + response.data.message);
    return FAIL;
  }
};

export const userPreRestPassword = async (email) => {
  var response = await preResetPassword(email);
  if (response.status === STATUS_SUCCESS) {
    saveState(TOKEN, response.data.data.token);
    return SUCCESS;
  } else {
    console.log(response);
    window.alert("Error: " + response.data.message);
    return FAIL;
  }
};

export const userRestPassword = async (email, password, token) => {
  var response = await resetPassword(email, password, token);
  if (response.status === STATUS_SUCCESS) {
    removeState(TOKEN);
    return SUCCESS;
  } else {
    console.log(response);
    window.alert("Error: " + response.data.message);
    return FAIL;
  }
};

// History Related Action
export const userGetHistory = async (id) => {
  var response = await getHistoryRecords(id);
  if (response.status === STATUS_SUCCESS) {
    saveState(HISTORY, response.data.data);
    return SUCCESS;
  } else {
    console.log(response);
    window.alert("Error: " + response.data.message);
    return FAIL;
  }
};

// Matching Related Actions 
export const userRequestMatching = async (id, difficulty) => {
  var response = await sendMatchRequest(id, difficulty);
  if (response.data.message === "success") {
    saveState(PEER, response.data.data.peer_id);
    saveState(ROLE, response.data.data.interviewer);
    return response
  } else {
    console.log(response.data.message);
    return response;
  }
};

export const userCancelMatching = async (id, difficulty) => {
  var response = await sendCancelMatchRequest(id, difficulty);
  console.log(response);
  if (response.status === STATUS_SUCCESS) {
    removeState(ROLE);
    removeState(PEER);
    return response;
  } else {
    // console.log(response.message);
    return FAIL;
  }
};

// Interview Related Actions 
export const userGetInterviewQuestion = async (id, peerId, difficulty) => {
  var response = await getInterviewQuestion(id, peerId, difficulty)
  if (response.status === STATUS_SUCCESS) {
    
    response.data.data.user_set.question = parseHtml(response.data.data.user_set.question)
    response.data.data.user_set.solution = parseHtml(response.data.data.user_set.solution)

    response.data.data.peer_set.question = parseHtml(response.data.data.peer_set.question)
    response.data.data.peer_set.solution = parseHtml(response.data.data.peer_set.solution)

    saveState(MY_QUESTION, response.data.data.user_set);
    saveState(PEER_QUESTION, response.data.data.peer_set);
    return SUCCESS
  } else {
    console.log(response.message)
    return FAIL
  }
};

export const userChangeRole = async (role) => {
  removeState(ROLE)
  role = role === "1" ? "0" : "1"
  saveState(ROLE, role)
  return SUCCESS
};

export const userLeaveInterview = async (id, questionId, userAttempt) => {
  var response = await saveUserApproach(id, questionId, userAttempt)
  if (response.status === STATUS_CREATED) {
    await userGetHistory(id)
    removeState(PEER)
    removeState(ROLE)
    removeState(MY_QUESTION)
    removeState(PEER_QUESTION)
    return SUCCESS
  } else {
    console.log(response.message)
    return FAIL
  }
};

export const currentUser = (state) => state.user;

export default userSlice.reducer;