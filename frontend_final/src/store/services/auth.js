import axios from "axios";

const url = process.env.VUE_APP_ROOT_API;
function login(params) {
  return axios
    .post(
      `${url}/api/login`,
      new URLSearchParams({
        username: params.username,
        password: params.password,
      })
    )
    .then((response) => {
      return {
        data: response.data,
      };
    });
}

function signup(params) {
  return axios.post(`${url}/api/auth/register`, params).then((response) => {
    return {
      data: response.data,
    };
  });
}

function verify_signup(params) {
  return axios
    .post(`${url}/api/auth/register/verify`, params)
    .then((response) => {
      return {
        data: response.data,
      };
    });
}

export default {
  login,
  signup,
  verify_signup,
};
