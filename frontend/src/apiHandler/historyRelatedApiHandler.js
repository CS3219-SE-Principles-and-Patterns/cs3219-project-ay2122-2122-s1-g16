import axios from 'axios';

const client = axios.create({
  // baseURL: "http://localhost:8080/api/v1",
  baseURL: "https://peerprep-backend-service-mepxozmoha-as.a.run.app/api/v1/history"
});

export const getHistoryRecords = (id) => {
  console.log("getHistoryRecords api call with user id", id);

  return client
    .get(`/user/${id}`, { withCredentials: true })
    .then((response) => {
      console.log(response);
      return response;
    })
    .catch((error) => {
      console.log(error);
      return error.response;
    });
};

