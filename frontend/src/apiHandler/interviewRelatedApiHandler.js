import axios from 'axios';

const client = axios.create({
  // baseURL: "http://localhost:8080/api/v1/interview/"
  baseURL: "https://peerprep-backend-service-mepxozmoha-as.a.run.app/api/v1/interview/"
});

export const getInterviewQuestion = async (id, peerId, difficulty) => {
    console.log("getInterviewQuestion api call with id", id, "peerId", peerId, "difficulty", difficulty)

    return client.get(`/question?id=${id}&peer_id=${peerId}&difficulty=${difficulty}`, {withCredentials: true})
    .then((response) => {
        console.log(response)
        return response
    }).catch((error) => {
        console.log(error);
    });
};

export const saveUserApproach = async (id, questionId, userAttempt) => {
    console.log("saveUserApproach api call with user id", id, "questionId", questionId, "userAttempt", userAttempt)

    var data = JSON.stringify({
        "userId": id,
        "questionId": questionId,
        "answer": userAttempt
    });

    return client.post('/answer', data, {
        headers: { 
          'Content-Type': 'application/json'
        },
        withCredentials: true
    }).then((response) => {
        console.log(response)
        return response
    }).catch((error) => {
        console.log(error);
    });
};