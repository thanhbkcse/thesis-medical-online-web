import axios from "axios";

// const url = "http://13.40.75.147:8080";
const url = process.env.VUE_APP_ROOT_API;

function profile_list(params) {
  axios.defaults.headers.common = { Authorization: `Bearer ${params.token}` };
  return axios.get(`${url}/api/profiles`, {}).then((response) => {
    return {
      data: response.data,
    };
  });
}

function add_new_profile(params) {
  axios.defaults.headers.common = { Authorization: `Bearer ${params.token}` };
  return axios.post(`${url}/api/profiles`, params.data).then((response) => {
    return {
      data: response.data,
    };
  });
}

function remove_profile(params) {
  axios.defaults.headers.common = { Authorization: `Bearer ${params.token}` };
  return axios.delete(`${url}/api/profiles/${params.data}`).then((response) => {
    return {
      data: response.data,
    };
  });
}

function edit_profile(params) {
  axios.defaults.headers.common = { Authorization: `Bearer ${params.token}` };
  return axios.patch(`${url}/api/profiles`, params.data).then((response) => {
    return {
      data: response.data,
    };
  });
}

export default {
  profile_list,
  add_new_profile,
  remove_profile,
  edit_profile,
};
