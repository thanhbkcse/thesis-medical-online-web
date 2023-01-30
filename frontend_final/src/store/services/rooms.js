import axios from "axios";
// const url = "http://13.40.75.147:8080";
const url = process.env.VUE_APP_ROOT_API;
function get_room_list(params) {
  axios.defaults.headers.common = { Authorization: `Bearer ${params.token}` };
  return axios.get(`${url}/api/rooms`, {}).then((response) => {
    return {
      data: response.data,
    };
  });
}

function add_new_room(params) {
  axios.defaults.headers.common = { Authorization: `Bearer ${params.token}` };
  return axios.post(`${url}/api/rooms`, params.data).then((response) => {
    return {
      data: response.data,
    };
  });
}

export default {
  get_room_list,
  add_new_room,
};
