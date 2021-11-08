import axios from 'axios';

const client = axios.create({
  // baseURL: "http://localhost:8080/api/v1/match/"
  baseURL: "https://peerprep-backend-service-mepxozmoha-as.a.run.app/api/v1/match"
});

export const sendMatchRequest = async (id, difficulty) => {
  console.log("sendMatchRequest api call with user id", id, "difficulty", difficulty)
  
  return client.get(`/queue?id=${id}&difficulty=${difficulty}`, {withCredentials: true})
  .then((response) => {
    console.log(response);
    return response;
  }).catch((error) => {
    console.log(error);
  });
};

export const sendCancelMatchRequest = async (id, difficulty) => {
  console.log("sendCancelMatchRequest with user id", id, "difficulty", difficulty);

  return client.get(`/dequeue?id=${id}&difficulty=${difficulty}`, {withCredentials: true})
  .then((response) => {
    console.log(response);
    return response;
  }).catch((error) => {
    console.log(error);
  });
};
