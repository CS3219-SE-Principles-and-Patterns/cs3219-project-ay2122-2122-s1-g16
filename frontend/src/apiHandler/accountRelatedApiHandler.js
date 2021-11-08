import axios from 'axios';

const client = axios.create({
  // baseURL: "http://localhost:8080/api/v1",
  baseURL: "https://peerprep-backend-service-mepxozmoha-as.a.run.app/api/v1"
});

export const login = (email, password) => {
  console.log("login api call with email", email);

  var data = new FormData();
  data.append("email", email);
  data.append("password", password);

  return client
    .post("/account/login", data, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
      withCredentials: true,
    })
    .then((response) => {
      console.log(response);
      return response;
    })
    .catch((error) => {
      console.log(error);
      return error.response;
    });
};

export const logout = () => {
  console.log("logout api call");

  return client
    .get("/account/logout", { withCredentials: true })
    .then((response) => {
      console.log(response);
      return response;
    })
    .catch((error) => {
      console.log(error);
      return error.response;
    });
};

export const register = (email, username, password) => {
  console.log("register api call with email", email, "username", username);

  var data = JSON.stringify({
    email: email,
    username: username,
    password: password,
  });

  return client
    .post("/account/register", data, {
      headers: {
        "Content-Type": "application/json",
      },
      withCredentials: true,
    })
    .then((response) => {
      console.log(response);
      return response;
    })
    .catch((error) => {
      console.log(error);
      return error.response;
    });
};

export const preResetPassword = (email) => {
  console.log("preResetPassword api cal with email", email);

  return client
    .post(`/account/password/pre-reset?email=${email}`, {
      withCredentials: true,
    })
    .then((response) => {
      console.log(response);
      return response;
    })
    .catch((error) => {
      console.log(error);
      return error.response;
    });
};

export const resetPassword = (email, password, token) => {
  console.log("resetPassword api call with email", email);

  const data = JSON.stringify({
    email: email,
    password: password,
    token: token,
  });
  return client
    .put("/account/password/reset", data, {
      headers: {
        "Content-Type": "application/json",
      },
      withCredentials: true,
    })
    .then((response) => {
      console.log(response);
      return response;
    })
    .catch((error) => {
      console.log(error);
      return error.response;
    });
};

export const getUserProfile = (email) => {
  console.log("getUserProfile api call with email", email);

  return client
    .get(`/profile/email?email=${email}`, { withCredentials: true })
    .then((response) => {
      console.log(response);
      return response;
    })
    .catch((error) => {
      console.log(error);
      return error.response;
    });
};

export const updateUserName = (id, newUsername) => {
  console.log("updateUserName api call with id", id, "username", newUsername);

  var data = JSON.stringify({
    id: id,
    username: newUsername,
  });

  return client
    .put("/profile", data, {
      headers: {
        "Content-Type": "application/json",
      },
      withCredentials: true,
    })
    .then((response) => {
      console.log(response);
      return response;
    })
    .catch((error) => {
      console.log(error);
      return error.response;
    });
};